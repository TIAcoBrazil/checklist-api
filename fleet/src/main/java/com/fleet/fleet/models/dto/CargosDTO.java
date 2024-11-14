package com.fleet.fleet.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CargosDTO {

    private List<String> cargos;

    private String driverId;

    private String carPlate;
}
