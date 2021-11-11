package com.dbc.pessoaapi.dto;

import com.dbc.pessoaapi.entity.TipoContato;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ContatoDTO {
    private Integer idContato;
    private Integer idPessoa;
    @Size(max = 1)
    @NotNull
    private TipoContato tipoContato;
    private String numero;
    private String descricao;
}
