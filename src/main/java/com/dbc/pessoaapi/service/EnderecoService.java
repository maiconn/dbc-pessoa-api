package com.dbc.pessoaapi.service;


import com.dbc.pessoaapi.dto.PageDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoCreateDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoDTO;
import com.dbc.pessoaapi.entity.EnderecoEntity;
import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import com.dbc.pessoaapi.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmailService emailService;

    private final String NOT_FOUND_MESSAGE = "{idEndereco} nao encontrado";


    public EnderecoDTO create(Integer idPessoa, EnderecoCreateDTO enderecoCreateDTO) throws EntidadeNaoEncontradaException {
        PessoaEntity pessoaEntity = pessoaService.returnPersonById(idPessoa);
        EnderecoEntity enderecoEntity = returnEntity(enderecoCreateDTO);
        enderecoEntity.setPessoas(Set.of(pessoaEntity));
        EnderecoDTO enderecoDTO = retornarDTO(enderecoRepository.save(enderecoEntity));
        enderecoDTO.setIdPessoa(idPessoa);
        return enderecoDTO;
    }

    public PageDTO<EnderecoDTO> list(Integer pagina, Integer tamanhoDasPaginas) {
        Page<EnderecoEntity> all = enderecoRepository.findAll(PageRequest.of(pagina, tamanhoDasPaginas));
        List<EnderecoDTO> content = all.getContent().stream()
                .map(this::retornarDTO)
                .toList();
        return new PageDTO<>(all.getTotalElements(), all.getTotalPages(), pagina, tamanhoDasPaginas, content);
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoCreateDTO enderecoAtualizadoDto) throws EntidadeNaoEncontradaException {
        verificarId(idEndereco);

        PessoaEntity pessoaAtualizada = pessoaService.returnPersonById(enderecoAtualizadoDto.getIdPessoa());
        EnderecoEntity enderecoAtualizado = returnEntity(enderecoAtualizadoDto);

        enderecoAtualizado.setIdEndereco(idEndereco);
        enderecoAtualizado.setPessoas(Set.of(pessoaAtualizada));

        EnderecoDTO retornoEndereco = retornarDTO(enderecoRepository.save(enderecoAtualizado));
        retornoEndereco.setIdPessoa(enderecoAtualizadoDto.getIdPessoa());
        return retornoEndereco;
    }

    public void delete(Integer id) throws EntidadeNaoEncontradaException {
        EnderecoEntity enderecoEntityRecuperado = recuperarEnderecoPorIdEndereco(id);
        enderecoRepository.delete(enderecoEntityRecuperado);
    }

    public EnderecoDTO listByAddressId(Integer id) throws EntidadeNaoEncontradaException {
        verificarId(id);
        return enderecoRepository.findById(id).stream()
                .map(this::retornarDTO)
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(NOT_FOUND_MESSAGE));
    }

    //Uteis-------------------------------------------
    public EnderecoEntity recuperarEnderecoPorIdEndereco(Integer id) throws EntidadeNaoEncontradaException {
        return enderecoRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(NOT_FOUND_MESSAGE));
    }

    private void verificarId(Integer id) throws EntidadeNaoEncontradaException {
        enderecoRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(NOT_FOUND_MESSAGE));
    }

    public EnderecoEntity returnEntity(EnderecoCreateDTO dto) {
        return objectMapper.convertValue(dto, EnderecoEntity.class);
    }

    public EnderecoDTO retornarDTO(EnderecoEntity enderecoEntity) {
        EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoEntity, EnderecoDTO.class);
        enderecoEntity.getPessoas().stream()
                .findFirst()
                .ifPresent(pessoaEntity -> enderecoDTO.setIdPessoa(pessoaEntity.getIdPessoa()));
        return enderecoDTO;
    }
}
