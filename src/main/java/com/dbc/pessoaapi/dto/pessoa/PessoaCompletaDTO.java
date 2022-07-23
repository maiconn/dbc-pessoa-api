package com.dbc.pessoaapi.dto.pessoa;

import com.dbc.pessoaapi.dto.contato.ContatoDTO;
import com.dbc.pessoaapi.dto.endereco.EnderecoDTO;
import com.dbc.pessoaapi.dto.pet.PetDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaCompletaDTO extends PessoaDTO {

    @Schema(description = "Contatos da pessoa")
    private List<ContatoDTO> contatos;

    @Schema(description = "Enderecos da pessoa")
    private List<EnderecoDTO> enderecos;
}
