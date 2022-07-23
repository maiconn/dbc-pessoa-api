package com.dbc.pessoaapi.documentation;

import com.dbc.pessoaapi.dto.login.LoginCreateDTO;
import com.dbc.pessoaapi.dto.login.LoginDTO;
import com.dbc.pessoaapi.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthDocumentation {

    @Operation(summary = "Loga no sistema e retorna um novo token")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato criado"),
                    @ApiResponse(responseCode = "403", description = "Usuário ou senha inválidos"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    String auth(@Valid @RequestBody LoginCreateDTO loginDTO);

    @Operation(summary = "Cria um novo usuário no sistema")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o contato criado"),
                    @ApiResponse(responseCode = "403", description = "Usuário ou senha inválidos"),
                    @ApiResponse(responseCode = "500", description = "Exception gerada")
            }
    )
    LoginDTO create(@Valid @RequestBody LoginCreateDTO newUsuario) throws RegraDeNegocioException;
}
