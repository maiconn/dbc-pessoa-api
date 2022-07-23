package com.dbc.pessoaapi.dto.pessoa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRelatorioDTO {
    private Integer idPessoa;
    private String nomePessoa;
    private String email;
    private String nomePet;
    private String numeroContato;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
}
