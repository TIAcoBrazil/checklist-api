package com.fleet.fleet.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AnswersDTO {

    @NotNull
    @NotEmpty
    private List<Answer> answers;

    @NotNull
    private int checklistId;

    @Getter
    @AllArgsConstructor
    public static class Answer {

        @NotNull
        @NotBlank
        private String answer;

        @NotNull
        private int questionId;
    }

}
