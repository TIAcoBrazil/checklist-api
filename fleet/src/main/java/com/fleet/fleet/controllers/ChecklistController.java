package com.fleet.fleet.controllers;

import com.fleet.fleet.models.Checklist;
import com.fleet.fleet.models.dto.AnswersDTO;
import com.fleet.fleet.models.dto.ChecklistRequestDTO;
import com.fleet.fleet.services.ChecklistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Checklist> openCheck(@Valid @RequestBody AnswersDTO answersDTO) {
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
}
