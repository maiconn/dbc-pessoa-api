package com.dbc.pessoaapi.controller;

import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paginacao")
@RequiredArgsConstructor
public class PaginacaoController {
    private final PessoaRepository pessoaRepository;

    @GetMapping("/lista-paginada")
    public Page<PessoaEntity> listaPaginada(
            @RequestParam Integer pagina,
            @RequestParam Integer quantidadeDeRegistrosPorPagina){
        Pageable pageable = PageRequest.of(pagina, quantidadeDeRegistrosPorPagina);
        Page<PessoaEntity> paginaDoBanco = pessoaRepository.findAll(pageable);
        return paginaDoBanco;
    }

    @GetMapping("/lista-paginada-ordenada")
    public Page<PessoaEntity> listaPaginadaOrdenada(
            @RequestParam Integer pagina,
            @RequestParam Integer quantidadeDeRegistrosPorPagina){
        Pageable pageable = PageRequest.of(pagina, // pagina que eu quero
                quantidadeDeRegistrosPorPagina, //quantidade de registros por pagina
                Sort.by("cpf").and(Sort.by("nome")) //ordenação (opcional)
        );
        Page<PessoaEntity> paginaDoBanco = pessoaRepository.findAll(pageable);
        return paginaDoBanco;
    }

    @GetMapping("/lista-por-nome-jpql")
    public Page<PessoaEntity> findByNomeJPQL(
            @RequestParam String nome,
            @RequestParam Integer pagina,
            @RequestParam(defaultValue = "2", required = false) Integer quantidadeDeRegistrosPorPagina){
        Pageable pageable = PageRequest.of(pagina, quantidadeDeRegistrosPorPagina);
        Page<PessoaEntity> paginaDoBanco = pessoaRepository.findByNomeJPQL("%"+nome+"%", pageable);
        return paginaDoBanco;
    }

    @GetMapping("/lista-por-nome-nativo")
    public Page<PessoaEntity> findByNomeNativo(
            @RequestParam String nome,
            @RequestParam Integer pagina,
            @RequestParam(defaultValue = "2", required = false) Integer quantidadeDeRegistrosPorPagina){
        Pageable pageable = PageRequest.of(pagina, quantidadeDeRegistrosPorPagina);
        Page<PessoaEntity> paginaDoBanco = pessoaRepository.findByNomeNativo("%"+nome+"%", pageable);
        return paginaDoBanco;
    }




}
