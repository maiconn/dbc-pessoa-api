package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.client.DadosPessoaisClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaComDadosService {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private DadosPessoaisClient dadosPessoaisClient;
    @Autowired
    private ObjectMapper objectMapper;


}
