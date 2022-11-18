package com.sistema.blog.service;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.entity.Comentario;
import com.sistema.blog.entity.Publicacion;
import com.sistema.blog.exceptions.BlogAppException;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repository.ComentarioRepository;
import com.sistema.blog.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {
    //Los dos repositorios que necesitamos para el servicio de comentario

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));//si no encuentra la publicacion con el id que le pasamos, lanza una excepcion

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepository.save(comentario);

        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacion(Long publicacionId) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        if (!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudComentarioDTO) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        if (!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        comentario.setNombre(solicitudComentarioDTO.getNombre());
        comentario.setEmail(solicitudComentarioDTO.getEmail());
        comentario.setContenido(solicitudComentarioDTO.getContenido());

        Comentario comentarioActualizado = comentarioRepository.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));
        if (!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        comentarioRepository.delete(comentario);
    }

    //--------------------------------------------------------------------------------
    //*******CON MODEL MAPPER********
    private ComentarioDTO mapearDTO(Comentario comentario) {//convertir entidad Comentario a DTO
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);

        return comentarioDTO;
    }

    private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {//convertir DTO a entidad Comentario
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);

        return comentario;
    }


    //*****HECHOS A MANO*****
    /*private ComentarioDTO mapearDTO(Comentario comentario) {//convertir entidad Comentario a DTO
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setNombre(comentario.getNombre());
        comentarioDTO.setEmail(comentario.getEmail());
        comentarioDTO.setContenido(comentario.getContenido());

        return comentarioDTO;
    }

    private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {//convertir DTO a entidad Comentario
        Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setContenido(comentarioDTO.getContenido());

        return comentario;
    }*/
}
