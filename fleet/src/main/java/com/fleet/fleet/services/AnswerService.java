package com.fleet.fleet.services;

import com.fleet.fleet.repositories.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerService {

    private AnswerRepository answerRepository;
}
