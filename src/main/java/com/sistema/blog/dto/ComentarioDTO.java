package com.sistema.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
public class ComentarioDTO {

    @Getter @Setter
    private Long id;
    @NotEmpty(message = "El nombre no puede estar vacio")
    @Getter @Setter
    private String nombre;
    @NotEmpty
    @Email(message = "El email debe ser valido")
    @Getter @Setter
    private String email;
    @NotEmpty
    @Size(min = 10, message = "El comentario debe tener al menos 10 caracteres")
    @Getter @Setter
    private String contenido;


}
