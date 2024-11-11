package com.fleet.fleet.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChecklistRequestDTO {

    @NotNull
    @NotBlank
    private String carPlate;

    @NotNull
    private int driverId;

    @NotNull
    @NotBlank
    private String route;

}
