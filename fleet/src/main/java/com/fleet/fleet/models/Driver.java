package com.fleet.fleet.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "pcempr")
@Data
public class Driver {

    @Id
    @Column(name = "matricula")
    private int driverId;

    @Column(name = "nome")
    private String name;

    @Column(name = "situacao")
    private String isActive;

    @Column(name = "codsetor")
    private int sectorId;

}
