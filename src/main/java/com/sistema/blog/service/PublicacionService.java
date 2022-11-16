package com.sistema.blog.service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;

import java.util.List;

public interface PublicacionService {
    //metodos para el servicio de publicacion
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int tamanoDePagina, String ordenarPor, String sortDir);

    public PublicacionDTO obtenerPublicacionPorId(Long id);

    public PublicacionDTO actualizarPublicacion(Long id, PublicacionDTO publicacionDTO);

    public void eliminarPublicacion(Long id);

}
