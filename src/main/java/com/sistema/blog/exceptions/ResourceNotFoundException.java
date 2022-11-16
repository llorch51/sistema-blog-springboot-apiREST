package com.sistema.blog.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private String nombreDelRecurso;
    @Getter @Setter
    private String nombreDelCampo;
    @Getter @Setter
    private Object valorDelCampo;

    //para mandar cuando no se encuentra un recurso/publicacion
    public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, Object valorDelCampo) {
        super(String.format("%s no encontrada con %s : '%s'", nombreDelRecurso, nombreDelCampo, valorDelCampo));
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;
    }
}

