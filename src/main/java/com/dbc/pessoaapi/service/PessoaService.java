package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.dto.ContatoDTO;
import com.dbc.pessoaapi.dto.PessoaCreateDTO;
import com.dbc.pessoaapi.dto.PessoaDTO;
import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.exceptions.RegraDeNegocioException;
import com.dbc.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final ObjectMapper objectMapper;

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws Exception {
        PessoaEntity pessoaEntity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        PessoaEntity pessoaCriada = pessoaRepository.save(pessoaEntity);
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaCriada, PessoaDTO.class);
        return pessoaDTO;
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.findAll().stream()
                .map(pessoa -> {
                    PessoaDTO pessoaDTO = objectMapper.convertValue(pessoa, PessoaDTO.class);
                    pessoaDTO.setContatosList(pessoa.getContatos().stream()
                            .map(contato -> {
                                ContatoDTO contatoDTO = objectMapper.convertValue(contato, ContatoDTO.class);
                                return contatoDTO;
                            })
                            .collect(Collectors.toList()));
                    return pessoaDTO;
                })
                .collect(Collectors.toList());
    }

    public PessoaEntity findById(Integer id) throws RegraDeNegocioException {
        PessoaEntity entity = pessoaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("pessoa não econtrada"));
        return entity;
    }

    public PessoaDTO getById(Integer id) throws RegraDeNegocioException {
        PessoaEntity entity = findById(id);
        PessoaDTO dto = objectMapper.convertValue(entity, PessoaDTO.class);
        return dto;
    }

    public PessoaDTO update(Integer id,
                            PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        PessoaEntity find = findById(id);
        find.setDataNascimento(pessoaCreateDTO.getDataNascimento());
        find.setNome(pessoaCreateDTO.getNome());
        find.setEmail(pessoaCreateDTO.getEmail());
        find.setCpf(pessoaCreateDTO.getCpf());
        PessoaEntity update = pessoaRepository.save(find);
        PessoaDTO dto = objectMapper.convertValue(update, PessoaDTO.class);
        return dto;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        PessoaEntity find = findById(id);
        pessoaRepository.delete(find);
    }
}
