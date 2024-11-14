package com.fleet.fleet.controllers;

import com.fleet.fleet.models.Question;
import com.fleet.fleet.models.views.QuestionView;
import com.fleet.fleet.services.QuestionsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/questions")
@AllArgsConstructor
public class QuestionsController {

    private QuestionsService questionsService;

    @GetMapping()
    public List<QuestionView> getQuestions() {
        return this.questionsService.getQuestions();
    }

    @GetMapping("/{questionId}")
    public Question getQuestionById(@PathVariable int questionId) {
        return this.questionsService.getQuestion(questionId);
    }
}
