package com.fleet.fleet.repositories;

import com.fleet.fleet.models.Question;
import com.fleet.fleet.models.views.QuestionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.isActive = 'A'")
    List<QuestionView> getActiveQuestions();
}
