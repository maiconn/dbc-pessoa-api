package com.dbc.pessoaapi.documentation;

import com.dbc.pessoaapi.dto.contato.ContatoCreateDTO;
import com.dbc.pessoaapi.dto.contato.ContatoDTO;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import com.dbc.pessoaapi.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ContatoDocumentation {

    @Operation(summary = "Cadastrar novo contato")
        @ApiResponses(
                value = {
                        @ApiResponse(responseCode = "200", description = "Retorna o contato criado"),
                        @ApiResponse(responseCode = "404", description = "{idPessoa} não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Exception gerada")
                }
        )
    ResponseEntity<ContatoDTO> post(Integer id, @Valid @RequestBody ContatoCreateDTO contato) throws EntidadeNaoEncontradaException, RegraDeNegocioException;


    @Operation(summary = "Listar contatos")
        @ApiResponses(
                value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos cadastrados"),
                        @ApiResponse(responseCode = "500", description = "Exception gerada")
                }
        )
    ResponseEntity<List<ContatoDTO>>  get();


    @Operation(summary = "Atualizar contato")
        @ApiResponses(
                value = {
                        @ApiResponse(responseCode = "200", description = "Atualiza os valores de um contato já existente"),
                        @ApiResponse(responseCode = "404", description = "{idContato} não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Exception gerada")
                }
        )
    ResponseEntity<ContatoDTO> put(Integer id, @Valid @RequestBody ContatoCreateDTO contatoAtualizado) throws  RegraDeNegocioException, EntidadeNaoEncontradaException;


    @Operation(summary = "Apagar contato")
        @ApiResponses(
                value = {
                        @ApiResponse(responseCode = "200", description = "Remove o contato da lista"),
                        @ApiResponse(responseCode = "404", description = "{idContato} não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Exception gerada")
                }
        )
    void delete(Integer id) throws RegraDeNegocioException, EntidadeNaoEncontradaException;


    @Operation(summary = "Listar contatos por pessoa")
        @ApiResponses(
                value = {
                        @ApiResponse(responseCode = "200", description = "Retorna a lista de contatos da pessoa desejada"),
                        @ApiResponse(responseCode = "404", description = "{idPessoa} não encontrado"),
                        @ApiResponse(responseCode = "500", description = "Exception gerada")
                }
        )
    ResponseEntity<List<ContatoDTO>> getByPersonId(Integer id) throws EntidadeNaoEncontradaException;
}
