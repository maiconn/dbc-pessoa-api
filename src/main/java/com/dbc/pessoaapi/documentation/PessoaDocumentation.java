package com.dbc.pessoaapi.documentation;

import com.dbc.pessoaapi.dto.PageDTO;
import com.dbc.pessoaapi.dto.pessoa.*;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public interface PessoaDocumentation {

    @Operation(summary = "Adicionar pessoa", description = "Adiciona uma pessoa na aplicação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Adiciona a pessoa"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    ResponseEntity<PessoaDTO> post(@Valid @RequestBody PessoaCreateDTO pessoa);

    @Operation(summary = "Atualizar pessoa", description = "Atualiza os dados cadastrados de uma pessoa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dados atualizados"),
                    @ApiResponse(responseCode = "404", description = "{idPessoa} não existe"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    ResponseEntity<PessoaDTO> put(Integer id, @Valid @RequestBody PessoaCreateDTO pessoaAtualizada) throws EntidadeNaoEncontradaException;

    @Operation(summary = "Listar pessoas", description = "lista todas as pessoas cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todas as pessoas cadastradas"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    ResponseEntity<PageDTO<PessoaDTO>> get(Integer pagina, Integer tamanhoDasPaginas);

    @Operation(summary = "Remover pessoa", description = "remove uma pessoa através do id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro removido"),
                    @ApiResponse(responseCode = "404", description = "{idPessoa} não existe"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    void delete(Integer id) throws EntidadeNaoEncontradaException;

    @Operation(summary = "Listar pessoas por nome", description = "Recebe um nome como parametro e retorna " +
            "uma lista de pessoas com o nome informado. Retorna vazio caso não exista.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista pessoas"),
                    @ApiResponse(responseCode = "500", description = "Server-side error")
            }
    )
    ResponseEntity<List<PessoaDTO>> getByNome(String nome);

    @Operation(summary = "Retornar pessoa por cpf", description = "Recebe um cpf como parametro e retorna " +
            "a pessoa que tenha o cpf informado. Retorna vazio caso não exista.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retornar pessoa"),
                    @ApiResponse(responseCode = "500", description = "Server-side error")
            }
    )
    ResponseEntity<PessoaDTO> getByCpf(String cpf);

    @Operation(summary = "Listar pessoas entre datas de nascimento", description = "Recebe duas datas como parâmetro (data inicial:{dtInicial}, data final:{dtFinal} " +
            "e lista as pessoas que nasceram dentro do intervalo entre as datas. Retorna vazio caso não exista.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar pessoas"),
                    @ApiResponse(responseCode = "500", description = "Server-side error")
            }
    )
    ResponseEntity<List<PessoaDTO>> getBetweenDataNascimento(LocalDate dtInicial, LocalDate dtFinal);

    @Operation(summary = "Listar pessoas com contatos", description = "Caso receba um {idPessoa} como parâmetro, será" +
            "retornado a pessoa específica do ID informado juntamente de seus contatos. \n " +
            "Caso não receba um parâmetro, será retornado a lista completa de pessoas com seus contatos.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar pessoas"),
                    @ApiResponse(responseCode = "404", description = "{idPessoa} não existe"),
                    @ApiResponse(responseCode = "500", description = "Server-side error")
            }
    )
    ResponseEntity<List<PessoaComContatoDTO>> getWithContato(Integer idPessoa) throws EntidadeNaoEncontradaException;

    @Operation(summary = "Listar pessoas com enderecos", description = "Caso receba um {idPessoa} como parâmetro, será" +
            "retornado a pessoa específica do ID informado juntamente de seus endereços. \n " +
            "Caso não receba um parâmetro, será retornado a lista completa de pessoas com seus enderecos.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar pessoas"),
                    @ApiResponse(responseCode = "404", description = "{idPessoa} não existe"),
                    @ApiResponse(responseCode = "500", description = "Server-side error")
            }
    )
    ResponseEntity<List<PessoaComEnderecoDTO>> getWithEndereco(Integer idPessoa) throws EntidadeNaoEncontradaException;
}
