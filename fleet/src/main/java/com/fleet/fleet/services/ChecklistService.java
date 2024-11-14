package com.fleet.fleet.services;

import com.fleet.fleet.exceptions.*;
import com.fleet.fleet.models.Answer;
import com.fleet.fleet.models.Checklist;
import com.fleet.fleet.models.dto.AnswersDTO;
import com.fleet.fleet.models.dto.CargosDTO;
import com.fleet.fleet.models.dto.ChecklistRequestDTO;
import com.fleet.fleet.models.enums.AnswerOption;
import com.fleet.fleet.models.enums.ChecklistStatus;
import com.fleet.fleet.models.enums.Route;
import com.fleet.fleet.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ChecklistService {

    private final String uploadDir = "C:\\Users\\ti.02\\Documents\\Projects\\uploadDir";

    private ChecklistRepository checklistRepository;
    private DriverRepository driverRepository;
    private VehicleRepository vehicleRepository;
    private AnswerRepository answerRepository;
    private CargoRepository cargoRepository;

    private static final int DRIVERS_SECTOR_ID = 23;

    public Checklist createChecklist(ChecklistRequestDTO checklist) {
        boolean driverExists = driverRepository.existsBySectorIdAndDriverId(DRIVERS_SECTOR_ID, checklist.getDriverId());

        if(!driverExists) {
            throw new DriverNotFoundException("Driver Not Found");
        }

        boolean vehicleExists = vehicleRepository.existsByPlate(checklist.getCarPlate());

        if(!vehicleExists) {
            throw new VehicleNotFoundException("Vehicle Not Found");
        }

        boolean openChecklist = checklistRepository.existsByCarPlateAndStatus(checklist.getCarPlate(), ChecklistStatus.NEW.getStatus());

        if(openChecklist) {
            throw new OpenChecklistExistsException("Duplicate Checklist");
        }

        boolean pendingChecklist = checklistRepository.existsByCarPlateAndStatus(checklist.getCarPlate(), ChecklistStatus.PENDING.getStatus());

        if(pendingChecklist) {
            throw new PendingChecklistException("Duplicate Checklist");
        }

        var vehicle = vehicleRepository.findByPlate(checklist.getCarPlate());

        if(vehicle.getSituation() == 'B') {
            throw new BlockedVehicleException("Blocked Vehicle");
        }

        var newChecklist = Checklist.builder()
                .driverId(checklist.getDriverId())
                .route(checklist.getRoute())
                .carPlate(checklist.getCarPlate())
                .status(ChecklistStatus.NEW.getStatus())
                .build();

        this.checklistRepository.save(newChecklist);

        return newChecklist;
    }

    public Checklist createAndModifyChecklists(int checklistID) {

        var startChecklist = checklistRepository.findById(checklistID).get();

        var newChecklistModel = Checklist.builder()
                .driverId(startChecklist.getDriverId())
                .route(Route.END.getRoute())
                .carPlate(startChecklist.getCarPlate())
                .status(ChecklistStatus.NEW.getStatus())
                .endChecklist(-1)
                .build();

        var newChecklist = this.checklistRepository.save(newChecklistModel);

        this.checklistRepository.updateEndChecklistAndStatusByChecklistId(startChecklist.getChecklistId(), newChecklistModel.getChecklistId());

        return newChecklist;
    }

    @Transactional()
    public Checklist openChecklist(AnswersDTO answersDTO) {
        this.saveAnswers(answersDTO);

        if (checkIfAnyNoCompliant(answersDTO)) {
            return this.blockVehicleAndSuspendChecklist(answersDTO.getChecklistId());
        }

        return this.createAndModifyChecklists(answersDTO.getChecklistId());
    }

    public List<Checklist> getChecklists(int driverId) {
        return this.checklistRepository.findAllByDriverIdAndStatusAndRoute(driverId);
    }

    public Checklist finishChecklist(AnswersDTO answersDTO) {
        this.saveAnswers(answersDTO);

        if (this.checkIfAnyNoCompliant(answersDTO)) {
            return this.blockVehicleAndSuspendChecklist(answersDTO.getChecklistId());
        }

        Checklist checklist = this.checklistRepository.findById(answersDTO.getChecklistId()).get();

        checklist.setStatus(ChecklistStatus.FINISHED.getStatus());

        this.checklistRepository.save(checklist);

        return checklist;
    }

    public void uploadPhoto(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        Path directory = Paths.get(this.uploadDir);

        try {
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }

            file.transferTo(new File(directory.toString(), fileName));
        } catch (IOException e) {
            throw new UploadPhotoException("Error while saving photo.");
        }

    }

    private Checklist blockVehicleAndSuspendChecklist(int checklistId) {
        var checklist = this.checklistRepository.findById(checklistId).get();
        checklist.setStatus(ChecklistStatus.PENDING.getStatus());
        this.checklistRepository.save(checklist);

        var vehicle = this.vehicleRepository.findByPlate(checklist.getCarPlate());
        vehicle.setSituation('B');
        this.vehicleRepository.save(vehicle);

        return checklist;
    }

    private void saveAnswers(AnswersDTO answersDTO) {
        answersDTO.getAnswers().forEach(answer -> {
            this.answerRepository.save(Answer.builder()
                    .checklistId(answersDTO.getChecklistId())
                    .questionId(answer.getQuestionId())
                    .answer(answer.getAnswer())
                    .build());
        });
    }

    private boolean checkIfAnyNoCompliant(AnswersDTO answersDTO) {
       return answersDTO.getAnswers().stream()
                .anyMatch(answer -> AnswerOption.NO_COMPLIANT.getAnswer().equals(answer.getAnswer()));
    }

    public ArrayList<String> validateCargos(CargosDTO cargosDTO) {

        ArrayList<String> failedCargos = new ArrayList<>();

        try {
            cargosDTO.getCargos().forEach(c -> {
                var cargo = this.cargoRepository.findByCargoId(Integer.parseInt(c));

                if(cargo == null || cargo.getDriverId() != Integer.parseInt(cargosDTO.getDriverId())) {
                    failedCargos.add(c);
                    return;
                }

                var vehicle = this.vehicleRepository.findById(cargo.getCarId());

                if (!Objects.equals(vehicle.get().getPlate(), cargosDTO.getCarPlate())) {
                    failedCargos.add(c);
                }
            });
        } catch (Exception e) {
            throw new DriverNotFoundException(e.getMessage());
        }

        return failedCargos;
    }

    public List<Answer> getNoCompliantAnswers(Integer checklistId) {
        return this.answerRepository.findByAnswerAndChecklistId(AnswerOption.NO_COMPLIANT.getAnswer(), checklistId);
    }

}