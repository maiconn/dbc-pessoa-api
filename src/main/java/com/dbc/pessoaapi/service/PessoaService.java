package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.client.DadosPessoaisClient;
import com.dbc.pessoaapi.dto.DadosPessoaisDTO;
import com.dbc.pessoaapi.dto.PessoaCreateDTO;
import com.dbc.pessoaapi.dto.PessoaDTO;
import com.dbc.pessoaapi.entity.PessoaEntity;
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
    private final EmailService emailService;
    private final DadosPessoaisClient dadosPessoaisClient;

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws Exception {
        PessoaEntity pessoaEntity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        PessoaEntity pessoaCriada = pessoaRepository.create(pessoaEntity);
        emailService.enviarEmailParaPessoa(pessoaCriada);
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaCriada, PessoaDTO.class);
        return pessoaDTO;
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.list().stream()
                .map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

    public PessoaDTO getById(Integer id) throws Exception {
        PessoaEntity entity = pessoaRepository.getById(id);
        DadosPessoaisDTO dadosPessoaisDTO = dadosPessoaisClient.getPorCpf(entity.getCpf());
        PessoaDTO dto = objectMapper.convertValue(entity, PessoaDTO.class);
        dto.setDadosPessoaisDTO(dadosPessoaisDTO);
        return dto;
    }

    public PessoaDTO update(Integer id,
                               PessoaCreateDTO pessoaCreateDTO) throws Exception {
        PessoaEntity entity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        PessoaEntity update = pessoaRepository.update(id, entity);
        PessoaDTO dto = objectMapper.convertValue(update, PessoaDTO.class);
        return dto;
    }

    public void delete(Integer id) throws Exception {
        pessoaRepository.delete(id);

    }
}
