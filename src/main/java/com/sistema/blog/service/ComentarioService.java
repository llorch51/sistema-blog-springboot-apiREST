package com.sistema.blog.service;

import com.sistema.blog.dto.ComentarioDTO;

public interface ComentarioService {
    //metodos para el servicio de comentario
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);


}
