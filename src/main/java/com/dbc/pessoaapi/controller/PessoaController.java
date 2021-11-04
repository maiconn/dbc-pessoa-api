package com.dbc.pessoaapi.controller;

import com.dbc.pessoaapi.client.DadosPessoaisClient;
import com.dbc.pessoaapi.dto.DadosPessoaisDTO;
import com.dbc.pessoaapi.dto.PessoaCreateDTO;
import com.dbc.pessoaapi.dto.PessoaDTO;
import com.dbc.pessoaapi.service.PessoaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Validated
@RequiredArgsConstructor
@Slf4j
public class PessoaController {
    private final PessoaService pessoaService;
    private final DadosPessoaisClient dadosPessoaisClient;

//    public PessoaController(PessoaService pessoaService){
//        this.pessoaService = pessoaService;
//    }

    @Value("${spring.application.name}")
    private String name;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("A INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        return new ResponseEntity<>(name, HttpStatus.ACCEPTED);
    }


    @PostMapping
    public PessoaDTO create(@RequestBody @Valid PessoaCreateDTO pessoaDTO) throws Exception {
        log.info("iniciando criação da pessoa");
        PessoaDTO pessoaEntityCriado = pessoaService.create(pessoaDTO);
        log.info("pessoa criada com sucesso!");
        return pessoaEntityCriado;
    }

    @ApiOperation(value = "Lista todas as pessoas")
    @GetMapping
    public List<PessoaDTO> list() {
        return pessoaService.list();
    }

    @GetMapping("/{idPessoa}")
    public PessoaDTO getById(@RequestParam("idPessoa") Integer idPessoa) throws Exception {
        return pessoaService.getById(idPessoa);
    }

    @ApiOperation(value = "Atualiza uma nova pessoa com o id informado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pessoa atualizada com sucesso!"),
            @ApiResponse(code = 400, message = "Pessoa com dados inconsistentes"),
            @ApiResponse(code = 500, message = "Exceção no sistema")
    })
    @PutMapping("/{idPessoa}")
    public PessoaDTO update(@PathVariable("idPessoa") Integer id,
                            @RequestBody @Valid PessoaCreateDTO pessoaCreateDTO) throws Exception {
        return pessoaService.update(id, pessoaCreateDTO);
    }

    @DeleteMapping("/{idPessoa}")
    public void delete(@PathVariable("idPessoa") Integer id) throws Exception {
        pessoaService.delete(id);
    }

    @GetMapping("/dados-pessoais")
    public List<DadosPessoaisDTO> listarDadosPessoais() {
        return dadosPessoaisClient.listar();
    }
}
