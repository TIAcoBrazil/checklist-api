package com.fleet.fleet.services;

import com.fleet.fleet.models.Question;
import com.fleet.fleet.models.views.QuestionView;
import com.fleet.fleet.repositories.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionsService {

    private QuestionRepository questionRepository;

    public List<QuestionView> getQuestions() {
         return questionRepository.getActiveQuestions();
    }

    public Question getQuestion(Integer questionId) {
        return questionRepository.findById(questionId.longValue()).get();
    }
}
