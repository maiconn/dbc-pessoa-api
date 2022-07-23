package com.dbc.pessoaapi.dto.login;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    private String login;
    private Integer idUsuario;
}

