package com.dbc.pessoaapi.dto.pessoa;

import com.dbc.pessoaapi.dto.dados.DadosPessoaisDTO;
import lombok.Data;

@Data
public class PessoaComDadosDTO {
    private PessoaDTO pessoaDTO;
    private DadosPessoaisDTO dadosPessoaisDTO;
}
