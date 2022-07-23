package com.dbc.pessoaapi.dto.pet;

import com.dbc.pessoaapi.enums.TipoPet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PetCreateDTO {

    @Schema(description = "Nome do pet", example = "Leona")
    @NotNull
    @NotBlank
    private String nome;
    @Schema(description = "Categoria do pet", example = "CACHORRO")
    @NotNull
    private TipoPet tipo;
}
