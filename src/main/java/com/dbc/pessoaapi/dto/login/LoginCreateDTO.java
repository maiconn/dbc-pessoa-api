package com.dbc.pessoaapi.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginCreateDTO {
    @NotNull
    @NotBlank
    @Schema(example = "admin")
    private String login;
    @NotNull
    @NotBlank
    @Schema(example = "123")
    private String senha;
}

