package com.sistema.blog.dto;


import com.sistema.blog.entity.Comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor
public class PublicacionDTO {//clase para enviar los datos de la publicacion, no tiene acceso a la base de datos como DAO

    @Getter @Setter
    private Long id;
    @NotEmpty//no puede estar vacio
    @Size(min = 2, message = "El titulo debe tener al menos 2 caracteres")
    @Getter @Setter
    private String titulo;
    @NotEmpty//no puede estar vacio
    @Size(min = 10, message = "La descripcion debe tener al menos 10 caracteres")
    @Getter @Setter
    private String descripcion;
    @NotEmpty//no puede estar vacio
    @Getter @Setter
    private String contenido;
    @Getter @Setter
    private Set<Comentario> comentarios;


}

