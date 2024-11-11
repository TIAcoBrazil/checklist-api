package com.fleet.fleet.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "pcveicul")
public class Vehicle {

    @Id
    @Column(name = "codveiculo")
    private int id;

    @Column(name = "placa")
    private String plate;
}
