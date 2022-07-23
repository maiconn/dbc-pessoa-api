package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.dto.contato.ContatoCreateDTO;
import com.dbc.pessoaapi.dto.contato.ContatoDTO;
import com.dbc.pessoaapi.entity.ContatoEntity;
import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import com.dbc.pessoaapi.exception.RegraDeNegocioException;
import com.dbc.pessoaapi.repository.ContatoRepository;
import com.dbc.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContatoService {
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String NOT_FOUND_MESSAGE = "{idContato} não encontrado";


    public ContatoDTO create(Integer id, ContatoCreateDTO contatoDto) throws EntidadeNaoEncontradaException {
        PessoaEntity pessoaEntity = pessoaService.returnPersonById(id);
        contatoDto.setIdPessoa(id);
        ContatoEntity contato = retornarEntidade(contatoDto);
        contato.setPessoa(pessoaEntity);
        return retornarDTO(contatoRepository.save(contato));
    }

    public List<ContatoDTO> list() {
        return contatoRepository.findAll().stream()
                .map(this::retornarDTO)
                .collect(Collectors.toList());
    }

    public ContatoDTO update(Integer id, ContatoCreateDTO contatoDto) throws EntidadeNaoEncontradaException {
        ContatoEntity contatoRecuperado = returnById(id);
        contatoRecuperado.setIdPessoa(contatoDto.getIdPessoa());
        contatoRecuperado.setTipoContato(contatoDto.getTipoContato());
        contatoRecuperado.setTelefone(contatoDto.getTelefone());
        contatoRecuperado.setDescricao(contatoDto.getDescricao());

        return retornarDTO(contatoRepository.save(contatoRecuperado));
    }

    public void delete(Integer id) throws RegraDeNegocioException, EntidadeNaoEncontradaException {
        ContatoEntity contatoRecuperado = returnById(id);
        contatoRepository.delete(contatoRecuperado);
    }

    public List<ContatoDTO> listByPersonId(Integer id) throws EntidadeNaoEncontradaException {
        pessoaService.verificarId(id);
        return contatoRepository.findAll().stream()
                .filter(contato -> contato.getIdPessoa().equals(id))
                .map(this::retornarDTO)
                .collect(Collectors.toList());
    }


    // Uteis-----------------------------------------------------------------------

    private ContatoEntity returnById(Integer id) throws EntidadeNaoEncontradaException {
        return contatoRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(NOT_FOUND_MESSAGE));
    }

    public ContatoEntity retornarEntidade(ContatoCreateDTO dto) {
        return objectMapper.convertValue(dto, ContatoEntity.class);
    }

    public ContatoDTO retornarDTO(ContatoEntity contatoEntity) {
        return objectMapper.convertValue(contatoEntity, ContatoDTO.class);
    }
}
