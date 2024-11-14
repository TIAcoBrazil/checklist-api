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
@Table(name = "pccarreg")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cargo {

    @Id
    @Column(name = "numcar")
    private Integer cargoId;

    @Column(name = "codveiculo")
    private Integer carId;

    @Column(name = "codmotorista")
    private Integer driverId;
}
