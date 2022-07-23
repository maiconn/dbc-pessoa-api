package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.dto.pet.PetCreateDTO;
import com.dbc.pessoaapi.dto.pet.PetDTO;
import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.entity.PetEntity;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import com.dbc.pessoaapi.exception.RegraDeNegocioException;
import com.dbc.pessoaapi.repository.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private ObjectMapper objectMapper;

    public PetDTO create(Integer idPessoa, PetCreateDTO petDto) throws EntidadeNaoEncontradaException, RegraDeNegocioException {
        PessoaEntity pessoaRecuperada = pessoaService.returnPersonById(idPessoa);
        verificarSeTemPet(pessoaRecuperada);

        PetEntity petEntity = retornarEntity(petDto);
        petEntity.setPessoa(pessoaRecuperada);

        PetDTO petCriado = retornarDTO(petRepository.save(petEntity));
        pessoaRecuperada.setPet(petEntity);
        pessoaService.saveEntity(pessoaRecuperada);

        petCriado.setIdPessoa(pessoaRecuperada.getIdPessoa());
        return petCriado;
    }

    public PetDTO update(Integer id, PetDTO petAtualizado) throws EntidadeNaoEncontradaException, RegraDeNegocioException {
        PetEntity petRecuperado = retornarPorId(id);
        PessoaEntity antigoDono = pessoaService.returnPersonById(petRecuperado.getPessoa().getIdPessoa());
        PessoaEntity pessoaAtualizada = pessoaService.returnPersonById(petAtualizado.getIdPessoa());

        if (verificarSeTemPet(pessoaAtualizada) && !Objects.equals(pessoaAtualizada.getIdPessoa(), antigoDono.getIdPessoa())) {
            throw new RegraDeNegocioException("Essa pessoa já tem um pet cadastrado!");
        }

        antigoDono.setPet(null);
        petRecuperado.setPessoa(pessoaAtualizada);
        petRecuperado.setNome(petAtualizado.getNome());
        petRecuperado.setTipo(petAtualizado.getTipo());

        PetDTO retornoPet = retornarDTO(petRepository.save(petRecuperado));
        pessoaAtualizada.setPet(petRecuperado);
        pessoaService.saveEntity(pessoaAtualizada);

        retornoPet.setIdPessoa(pessoaAtualizada.getIdPessoa());
        return retornoPet;
    }

    public List<PetDTO> getAll() {
        return petRepository.findAll().stream()
                .map(petEntity -> {
                    PetDTO petDto = retornarDTO(petEntity);
                    petDto.setIdPessoa(petEntity.getPessoa().getIdPessoa());
                    return petDto;
                }).toList();
    }

    public PetDTO getPet(Integer idPet) throws EntidadeNaoEncontradaException {
        PetEntity petRecuperado = retornarPorId(idPet);
        PetDTO retornoPet = retornarDTO(petRecuperado);
        retornoPet.setIdPessoa(petRecuperado.getPessoa().getIdPessoa());
        return retornoPet;
    }

    public void delete(Integer idPet) throws EntidadeNaoEncontradaException {
        PetEntity petRecuperado = retornarPorId(idPet);
        PessoaEntity pessoaAtualizada = pessoaService.returnPersonById(petRecuperado.getPessoa().getIdPessoa());
        pessoaAtualizada.setPet(null);
        pessoaService.saveEntity(pessoaAtualizada);
        petRepository.delete(petRecuperado);
    }

    //------------------------------------Auxiliares-------------------------------------
    public PetEntity retornarEntity(PetCreateDTO dto) {
        return objectMapper.convertValue(dto, PetEntity.class);
    }

    public PetDTO retornarDTO(PetEntity entity) {
        return objectMapper.convertValue(entity, PetDTO.class);
    }

    public PetEntity retornarPorId(Integer id) throws EntidadeNaoEncontradaException {
        return petRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new EntidadeNaoEncontradaException("{idPet} não encontrado"));
    }

    public boolean verificarSeTemPet(PessoaEntity pessoa) {
        if (pessoa.getPet() != null) {
            return true;
        }
        return false;
    }
}
