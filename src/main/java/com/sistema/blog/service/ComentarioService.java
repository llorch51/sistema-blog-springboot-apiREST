package com.sistema.blog.service;

import com.sistema.blog.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioService {
    //metodos para el servicio de comentario
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentariosPorPublicacion(Long publicacionId);

    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);

    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudComentarioDTO);

    public void eliminarComentario(Long publicacionId, Long comentarioId);


}
