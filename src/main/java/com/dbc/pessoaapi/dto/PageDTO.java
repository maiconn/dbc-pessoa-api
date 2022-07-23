package com.dbc.pessoaapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageDTO<T> {
    @Schema(description = "Total de elementos na página")
    private Long totalElements;
    @Schema(description = "Quantidade total de páginas")
    private Integer totalPages;
    @Schema(description = "Página solicitada (começa por zero)")
    private Integer page;
    @Schema(description = "Quantidade de registros por página solicitado")
    private Integer size;
    @Schema(description = "Lista de registros da página")
    private List<T> content;
}