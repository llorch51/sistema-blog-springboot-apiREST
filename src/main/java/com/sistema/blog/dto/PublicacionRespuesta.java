package com.sistema.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
public class PublicacionRespuesta {

    @Getter @Setter
    private List<PublicacionDTO> contenido;
    @Getter @Setter
    private int numeroDePagina;
    @Getter @Setter
    private int tamanoDePagina;
    @Getter @Setter
    private long totalElementos;
    @Getter @Setter
    private int totalPaginas;
    @Getter @Setter
    private boolean ultima;


}
