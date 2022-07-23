package com.dbc.pessoaapi.dto.pessoa;

import com.dbc.pessoaapi.dto.pet.PetDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PessoaComPetDTO extends PessoaDTO {
    @Schema(description = "pet relacionado com a pessoa, 1..1")
    private PetDTO pet;
}
