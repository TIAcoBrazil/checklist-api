package com.fleet.fleet.repositories;

import com.fleet.fleet.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByAnswerAndChecklistId(String answer, Integer answerId);
}
