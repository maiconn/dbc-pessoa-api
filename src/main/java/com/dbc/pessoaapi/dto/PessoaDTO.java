package com.dbc.pessoaapi.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class PessoaDTO {
    private Integer idPessoa;
    @NotEmpty
    @NotBlank
    @ToString.Exclude
    private String nome;

    @NotNull
    private LocalDate dataNascimento;

    @Size(max = 11, min = 11, message = "cpf deve conter 11 caracteres")
    @NotNull
    private String cpf;

    @Email
    @NotNull
    private String email;

    private DadosPessoaisDTO dadosPessoaisDTO;
}
