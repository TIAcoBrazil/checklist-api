package com.fleet.fleet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "abz_checklists")
@SequenceGenerator(name="CHECKLIST_ID_GENERATOR", sequenceName = "abz_checklists_seq", allocationSize = 1)
public class Checklist {

    @Id
    @Column(name = "numchecklist")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHECKLIST_ID_GENERATOR")
    private int checklistId;

    @Column(name = "data")
    private Date checklistDate;

    @Column(name = "placa")
    private String carPlate;

    @Column(name = "codmotorista")
    private int driverId;

    @Column(name = "status")
    private char status;

    @Column(name = "tipoderota")
    private String route;

    @Column(name = "numchecklistfinal")
    private int endChecklist;

    @PrePersist
    public void prePersist() {
        if (this.checklistDate == null) {
            this.checklistDate = new Date();
        }
    }

}
