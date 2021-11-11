package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.entity.ContatoEntity;
import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.exceptions.RegraDeNegocioException;
import com.dbc.pessoaapi.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final PessoaService pessoaService;

    public void delete(Long id) throws Exception {
        ContatoEntity contatoEntity = findById(id.intValue());
        contatoRepository.delete(contatoEntity);
    }

    public ContatoEntity create(Integer idPessoa, ContatoEntity contatoEntity) throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = pessoaService.findById(idPessoa);
        contatoEntity.setPessoaEntity(pessoaEntity);
        return contatoRepository.save(contatoEntity);
    }

    public ContatoEntity update(Integer id, ContatoEntity contatoEntity) throws RegraDeNegocioException {
        ContatoEntity contatoAtualizar = findById(id);
        contatoAtualizar.setTipoContato(contatoEntity.getTipoContato());
        contatoAtualizar.setDescricao(contatoEntity.getDescricao());
        contatoAtualizar.setNumero(contatoEntity.getNumero());
        return contatoRepository.save(contatoAtualizar);
    }


    public List<ContatoEntity> list() {
        return contatoRepository.findAll();
    }

//    public List<ContatoEntity> listByIdPessoa(Integer idPessoa) {
//        return contatoRepository.findAllByPessoa(idPessoa);
//    }

    public ContatoEntity findById(Integer idContato) throws RegraDeNegocioException {
        return contatoRepository.findById(idContato).orElseThrow(() -> new RegraDeNegocioException("contato não encontrado"));
    }
}
