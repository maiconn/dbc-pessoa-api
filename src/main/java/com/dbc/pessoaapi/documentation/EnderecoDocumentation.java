package com.dbc.pessoaapi.documentation;

import com.dbc.pessoaapi.dto.PageDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoCreateDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoDTO;
import com.dbc.pessoaapi.entity.EnderecoEntity;
import com.dbc.pessoaapi.exception.EntidadeNaoEncontradaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface EnderecoDocumentation {


    @Operation(summary = "Cadastrar novo endereço")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço criado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "{idPessoa} não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    ResponseEntity<EnderecoDTO> post(Integer idPessoa, @Valid @RequestBody EnderecoCreateDTO endereco) throws EntidadeNaoEncontradaException;

    @Operation(summary = "Listar endereços cadastrados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna toda a lista de endereços"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    ResponseEntity<PageDTO<EnderecoDTO>> get(Integer pagina, Integer tamanhoDasPaginas);

    @Operation(summary = "Atualizar endereço através do {idEndereco}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "{idEndereco} não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    ResponseEntity<EnderecoDTO> put(Integer id, @Valid @RequestBody EnderecoCreateDTO enderecoAtualizado) throws EntidadeNaoEncontradaException;

    @Operation(summary = "Deletar endereço através do {idEndereco}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Endereço deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "{idEndereco} não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    void delete(Integer id) throws EntidadeNaoEncontradaException;

    @Operation(summary = "Listar endereço por ID do endereço")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato através do ID"),
                    @ApiResponse(responseCode = "404", description = "{idEndereco} não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    ResponseEntity<EnderecoDTO> getByAddressId(Integer id) throws EntidadeNaoEncontradaException;

    @Operation(summary = "Listar endereços pelo País", description = "Lista os endereços que tenham o País com o mesmo " +
            "nome informado, caso não exista, será retornado um objeto vazio.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Executa a busca"),
                    @ApiResponse(responseCode = "400", description = "Client-side error, verifique se o corpo da requisição está correto"),
                    @ApiResponse(responseCode = "500", description = "Server-side error")
            }
    )
    ResponseEntity<List<EnderecoEntity>> getEnderecosByPais(String pais);

    @Operation(summary = "Listar endereços pelo {idPessoa}", description = "Lista os endereços que tenham o {idPessoa} como " +
            "residente ou proprietário. Caso a pessoa não tenha cadastro de endereço ou o ID não exista no banco de dados, será retornado um objeto vazio")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Executa a busca"),
                    @ApiResponse(responseCode = "400", description = "Client-side error, verifique se o corpo da requisição está correto"),
                    @ApiResponse(responseCode = "500", description = "Server-side error")
            }
    )
    ResponseEntity<List<EnderecoEntity>> getEnderecoByIdPessoa(@RequestParam("idPessoa") Integer id);
}
