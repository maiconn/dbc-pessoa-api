package com.dbc.pessoaapi.dto.contato;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContatoDTO extends ContatoCreateDTO {
    @Schema(description = "Identificador único do contato")
    private Integer idContato;
}
