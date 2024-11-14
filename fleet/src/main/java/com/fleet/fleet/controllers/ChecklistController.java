package com.fleet.fleet.controllers;

import com.fleet.fleet.models.Answer;
import com.fleet.fleet.models.Checklist;
import com.fleet.fleet.models.dto.AnswersDTO;
import com.fleet.fleet.models.dto.CargosDTO;
import com.fleet.fleet.models.dto.ChecklistRequestDTO;
import com.fleet.fleet.services.ChecklistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checklists")
@AllArgsConstructor
public class ChecklistController {

    private ChecklistService checklistService;

    @PostMapping
    public ResponseEntity<Checklist> createChecklist(@Valid @RequestBody ChecklistRequestDTO checklistRequest) {
        var checklist = checklistService.createChecklist(checklistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(checklist);
    }

    @PostMapping("/answers")
    public ResponseEntity<Checklist> openChecklist(@Valid @RequestBody AnswersDTO answersDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checklistService.openChecklist(answersDTO));
    }

    @PostMapping("/answers/finish")
    public ResponseEntity<Checklist> finishChecklist(@Valid @RequestBody AnswersDTO answersDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checklistService.finishChecklist(answersDTO));
    }

    @GetMapping("{driverId}")
    public ResponseEntity<List<Checklist>> getChecklists(@PathVariable Integer driverId) {
        return ResponseEntity.status(HttpStatus.OK).body(checklistService.getChecklists(driverId));
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadPhoto(@RequestParam("file")MultipartFile file) {
        this.checklistService.uploadPhoto(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/validate")
    public ResponseEntity<ArrayList<String>> validateCargo(@Valid @RequestBody CargosDTO cargosDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.checklistService.validateCargos(cargosDTO)) ;
    }

    @GetMapping("/answers/{checklistId}")
    public ResponseEntity<List<Answer>> getNoCompliantAnswers(@PathVariable Integer checklistId) {

        List<Answer> answers = checklistService.getNoCompliantAnswers(checklistId);

        if (answers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(answers);
    }
}
