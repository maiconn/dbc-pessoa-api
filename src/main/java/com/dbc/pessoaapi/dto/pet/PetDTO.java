package com.dbc.pessoaapi.dto.pet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PetDTO extends PetCreateDTO {
    private Integer idPet;
    private Integer idPessoa;
}
