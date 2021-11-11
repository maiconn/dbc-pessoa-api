package com.dbc.pessoaapi.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorPK implements Serializable {
    @Column(name = "id_professor")
    private Integer idProfessor;

    @Column(name = "id_universidade")
    private Integer idUniversidade;
}
