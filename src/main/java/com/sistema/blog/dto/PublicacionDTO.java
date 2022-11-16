package com.sistema.blog.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
public class PublicacionDTO {//clase para enviar los datos de la publicacion, no tiene acceso a la base de datos como DAO

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String titulo;
    @Getter @Setter
    private String descripcion;
    @Getter @Setter
    private String contenido;


}
