package com.fleet.fleet.services;

import com.fleet.fleet.exceptions.DriverNotFoundException;
import com.fleet.fleet.exceptions.OpenChecklistExists;
import com.fleet.fleet.exceptions.VehicleNotFoundException;
import com.fleet.fleet.models.Answer;
import com.fleet.fleet.models.Checklist;
import com.fleet.fleet.models.dto.AnswersDTO;
import com.fleet.fleet.models.dto.ChecklistRequestDTO;
import com.fleet.fleet.models.enums.AnswerOption;
import com.fleet.fleet.models.enums.ChecklistStatus;
import com.fleet.fleet.models.enums.Route;
import com.fleet.fleet.repositories.AnswerRepository;
import com.fleet.fleet.repositories.ChecklistRepository;
import com.fleet.fleet.repositories.DriverRepository;
import com.fleet.fleet.repositories.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
@AllArgsConstructor
public class ChecklistService {

    private ChecklistRepository checklistRepository;
    private DriverRepository driverRepository;
    private VehicleRepository vehicleRepository;
    private AnswerRepository answerRepository;

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
            throw new OpenChecklistExists("Duplicate Checklist");
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
            return this.blockVehicle(answersDTO.getChecklistId());
        }

        return this.createAndModifyChecklists(answersDTO.getChecklistId());
    }

    public List<Checklist> getChecklists(int driverId) {
        return this.checklistRepository.findAllByDriverIdAndStatusAndRoute(driverId);
    }

    public Checklist finishChecklist(AnswersDTO answersDTO) {
        this.saveAnswers(answersDTO);

        if (this.checkIfAnyNoCompliant(answersDTO)) {
            return this.blockVehicle(answersDTO.getChecklistId());
        }

        Checklist checklist = this.checklistRepository.findById(answersDTO.getChecklistId()).get();

        checklist.setStatus(ChecklistStatus.FINISHED.getStatus());

        this.checklistRepository.save(checklist);

        return checklist;
    }

    private Checklist blockVehicle(int checklistId) {
        var checklist = this.checklistRepository.findById(checklistId).get();
        checklist.setStatus(ChecklistStatus.PENDING.getStatus());
        this.checklistRepository.save(checklist);
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
}
