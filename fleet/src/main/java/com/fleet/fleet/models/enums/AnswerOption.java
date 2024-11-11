package com.fleet.fleet.models.enums;

import lombok.Getter;

@Getter
public enum AnswerOption {

    COMPLIANT("C"),
    NO_COMPLIANT("NC"),
    NOT_APPLICABLE("NA");

    private final String answer;

    AnswerOption(String answer) { this.answer = answer; }

}
