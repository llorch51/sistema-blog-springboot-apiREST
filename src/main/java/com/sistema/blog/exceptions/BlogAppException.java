package com.sistema.blog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


public class BlogAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private HttpStatus estado;
    @Getter @Setter
    private String mensaje;

    public BlogAppException(HttpStatus estado, String mensaje) {
        super(mensaje);
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public BlogAppException(HttpStatus estado, String mensaje, String mensaje1) {
        super(mensaje);
        this.estado = estado;
        this.mensaje = mensaje;
        this.mensaje = mensaje1;
    }

}
