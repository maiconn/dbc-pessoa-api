package com.dbc.pessoaapi.controller;

import com.dbc.pessoaapi.documentation.EnderecoDocumentation;
import com.dbc.pessoaapi.dto.PageDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoCreateDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoDTO;
import com.dbc.pessoaapi.entity.EnderecoEntity;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import com.dbc.pessoaapi.repository.EnderecoRepository;
import com.dbc.pessoaapi.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/endereco")
@Validated
public class EnderecoController implements EnderecoDocumentation {
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private EnderecoRepository enderecoRepository;



    @PostMapping("/{idPessoa}")
    public ResponseEntity<EnderecoDTO> post(Integer idPessoa, @RequestBody @Valid EnderecoCreateDTO endereco) throws EntidadeNaoEncontradaException {
        return ResponseEntity.ok(enderecoService.create(idPessoa , endereco));
    }


    @GetMapping
    public ResponseEntity<PageDTO<EnderecoDTO>>  get(@RequestParam(required = false, defaultValue = "0") Integer pagina,
                                                     @RequestParam(required = false, defaultValue = "20") Integer tamanhoDasPaginas) {
        return ResponseEntity.ok(enderecoService.list(pagina, tamanhoDasPaginas));
    }


    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> put(@PathVariable("idEndereco") Integer id,
                                 @RequestBody @Valid EnderecoCreateDTO enderecoAtualizado) throws EntidadeNaoEncontradaException {
        return ResponseEntity.ok(enderecoService.update(id, enderecoAtualizado));
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> getByAddressId(@PathVariable("idEndereco") Integer id) throws EntidadeNaoEncontradaException {
        return ResponseEntity.ok(enderecoService.listByAddressId(id));
    }


    @DeleteMapping("/{idEndereco}")
    public void delete(@PathVariable("idEndereco") Integer id) throws EntidadeNaoEncontradaException {
        enderecoService.delete(id);
    }

    @GetMapping("/retorna-por-pais")
    public ResponseEntity<List<EnderecoEntity>> getEnderecosByPais(@RequestParam("País") String pais) {
        return ResponseEntity.ok(enderecoRepository.listEnderecoByPais(pais));
    }

    @GetMapping("/retorna-por-id-pessoa")
    public ResponseEntity<List<EnderecoEntity>> getEnderecoByIdPessoa(@RequestParam("idPessoa") Integer id) {
        return ResponseEntity.ok(enderecoRepository.listEnderecoByIdPessoa(id));
    }

}
