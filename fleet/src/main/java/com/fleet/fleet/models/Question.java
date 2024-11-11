package com.fleet.fleet.models;

import com.fleet.fleet.models.enums.CarType;
import com.fleet.fleet.models.enums.Risk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "abz_checklists_perguntas")
public class Question {

    @Id
    @Column(name = "codpergunta")
    private int questionId;

    @Column(name = "pergunta")
    private String question;

    @Column(name = "tipocarro")
    private String carType;

    @Column(name = "obrigatorio")
    private String isMandatory;

    @Column(name = "situacao")
    private String isActive;

    @Column(name = "graurisco")
    private String risk;

}
