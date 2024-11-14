package com.fleet.fleet.models;

import com.fleet.fleet.models.enums.AnswerOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "abz_checklists_respostas")
@SequenceGenerator(name="ANSWER_ID_GENERATOR", sequenceName = "abz_checklist_respostas_seq", allocationSize = 1)
public class Answer {

    @Id
    @Column(name = "idresposta")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_ID_GENERATOR")
    private int answerId;

    @Column(name = "codpergunta")
    private int questionId;

    @Column(name = "numchecklist")
    private int checklistId;

    @Column(name = "resposta")
    private String answer;

    @Column(name = "resolvido")
    private Integer resolved;

}
