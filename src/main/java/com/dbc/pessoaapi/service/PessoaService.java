package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.dto.PageDTO;
import com.dbc.pessoaapi.dto.contato.ContatoDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoDTO;
import com.dbc.pessoaapi.dto.pessoa.*;
import com.dbc.pessoaapi.dto.pet.PetDTO;
import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import com.dbc.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private final String NOT_FOUND_MESSAGE = "ID da pessoa nao encontrada";


    public PessoaDTO create(PessoaCreateDTO pessoaDto) {
        PessoaEntity pessoaEntity = retornarEntity(pessoaDto);
        PessoaEntity pessoaCriada = pessoaRepository.save(pessoaEntity);
        PessoaDTO retornoPessoa = retornarDTO(pessoaCriada);
        retornoPessoa.setIdPessoa(pessoaCriada.getIdPessoa());
        return retornoPessoa;
    }

    public PessoaDTO update(Integer id, PessoaCreateDTO pessoaDto) throws EntidadeNaoEncontradaException {
        PessoaEntity pessoaRecuperada = returnPersonById(id);

        pessoaRecuperada.setCpf(pessoaDto.getCpf());
        pessoaRecuperada.setEmail(pessoaDto.getEmail());
        pessoaRecuperada.setDataNascimento(pessoaDto.getDataNascimento());
        pessoaRecuperada.setNome(pessoaDto.getNome());

        return retornarDTO(pessoaRepository.save(pessoaRecuperada));
    }

    public void delete(Integer id) throws EntidadeNaoEncontradaException {
        PessoaEntity pessoaEntityRecuperada = returnPersonById(id);
        pessoaRepository.delete(pessoaEntityRecuperada);
    }

    public PageDTO<PessoaDTO> list(Integer pagina, Integer tamanhoDasPaginas) {
        Page<PessoaEntity> all = pessoaRepository.findAll(PageRequest.of(pagina, tamanhoDasPaginas));
        List<PessoaDTO> content = all.getContent().stream()
                .map(this::retornarDTO)
                .toList();

        return new PageDTO<>(all.getTotalElements(), all.getTotalPages(), pagina, tamanhoDasPaginas, content);
    }

    public List<PessoaDTO> listByName(String nome) {
        return pessoaRepository.findByNomeContainsIgnoreCase(nome).stream()
                .map(this::retornarDTO)
                .collect(Collectors.toList());
    }

    public PessoaDTO returnByCpf(String cpf) {
        return retornarDTO(pessoaRepository.findByCpf(cpf));
    }

    public List<PessoaDTO> listByDataNascimento(LocalDate dataInicial, LocalDate dataFinal) {
        return pessoaRepository.findByDataNascimentoBetween(dataInicial, dataFinal).stream()
                .map(this::retornarDTO)
                .collect(Collectors.toList());
    }

    //--------------------------------OPERAÇÕES COMPOSTAS---------------------------------------
    public List<PessoaComContatoDTO> listWithContato(Integer idPessoa) throws EntidadeNaoEncontradaException {
        if (idPessoa == null) {
            return pessoaRepository.findAll().stream()
                    .map(this::getPessoaComContatoDTO).toList();
        } else {
            verificarId(idPessoa);
            return pessoaRepository.findById(idPessoa).stream()
                    .map(this::getPessoaComContatoDTO).toList();
        }
    }

    public List<PessoaComEnderecoDTO> listWithEndereco(Integer idPessoa) throws EntidadeNaoEncontradaException {
        if (idPessoa == null) {
            return pessoaRepository.findAll().stream()
                    .map(this::getPessoaComEnderecoDTO).toList();
        } else {
            verificarId(idPessoa);
            return pessoaRepository.findById(idPessoa).stream()
                    .map(this::getPessoaComEnderecoDTO).toList();
        }
    }

    public List<PessoaComPetDTO> listWithPet(Integer idPessoa) throws EntidadeNaoEncontradaException {
        if (idPessoa == null) {
            return pessoaRepository.findAll().stream()
                    .map(this::getPessoaComPetDTO).toList();
        } else {
            verificarId(idPessoa);
            return pessoaRepository.findById(idPessoa).stream()
                    .map(this::getPessoaComPetDTO).toList();
        }
    }

    public List<PessoaCompletaDTO> completeList(Integer idPessoa) {
        if (idPessoa == null) {
            return pessoaRepository.findAll().stream()
                    .map(this::getPessoaCompletaDTO).toList();
        } else {
            return pessoaRepository.findById(idPessoa).stream()
                    .map(this::getPessoaCompletaDTO).toList();
        }
    }

    public List<PessoaRelatorioDTO> gerarRelatorio(Integer id) {
        return pessoaRepository.listRelatorioPessoa(id);
    }


    //-----------------------------------UTEIS-----------------------------------------

    public void verificarId(Integer idPessoa) throws EntidadeNaoEncontradaException {
        pessoaRepository.findById(idPessoa).stream()
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(NOT_FOUND_MESSAGE));
    }

    public PessoaEntity returnPersonById(Integer id) throws EntidadeNaoEncontradaException {
        return pessoaRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException(NOT_FOUND_MESSAGE));
    }

    private PessoaComContatoDTO getPessoaComContatoDTO(PessoaEntity pessoaEntity) {
        PessoaComContatoDTO pessoasComContato = objectMapper.convertValue(retornarDTO(pessoaEntity), PessoaComContatoDTO.class);
        pessoasComContato.setContatos(pessoaEntity.getContatos().stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList()));
        return pessoasComContato;
    }

    private PessoaComEnderecoDTO getPessoaComEnderecoDTO(PessoaEntity pessoaEntity) {
        PessoaComEnderecoDTO pessoasComEndereco = objectMapper.convertValue(retornarDTO(pessoaEntity), PessoaComEnderecoDTO.class);
        pessoasComEndereco.setEnderecos(pessoaEntity.getEnderecos().stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList()));
        return pessoasComEndereco;
    }

    private PessoaComPetDTO getPessoaComPetDTO(PessoaEntity pessoaEntity) {
        PessoaComPetDTO pessoasComPet = objectMapper.convertValue(retornarDTO(pessoaEntity), PessoaComPetDTO.class);
        pessoasComPet.setPet(objectMapper.convertValue(pessoaEntity.getPet(), PetDTO.class));
        return pessoasComPet;
    }

    private PessoaCompletaDTO getPessoaCompletaDTO(PessoaEntity pessoaEntity) {
        PessoaCompletaDTO pessoasCompletas = objectMapper.convertValue(retornarDTO(pessoaEntity), PessoaCompletaDTO.class);
        pessoasCompletas.setContatos(pessoaEntity.getContatos().stream()
                .map(contato -> objectMapper.convertValue(contato, ContatoDTO.class))
                .collect(Collectors.toList()));
        pessoasCompletas.setEnderecos(pessoaEntity.getEnderecos().stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList()));
        return pessoasCompletas;
    }

    public PessoaEntity saveEntity(PessoaEntity pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public PessoaEntity retornarEntity(PessoaCreateDTO dto) {
        return objectMapper.convertValue(dto, PessoaEntity.class);
    }

    public PessoaDTO retornarDTO(PessoaEntity pessoaEntity) {
        return objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
    }
}
