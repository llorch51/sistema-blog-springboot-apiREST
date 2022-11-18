package com.sistema.blog.service;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.entity.Publicacion;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired//una instancia compartida
    private PublicacionRepository publicacionRepository;//inyecta el repositorio. una instancia de la interfaz

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        //convertimos DTO a Entidad(una instancia de la clase Publicacion que se insertar´´a en la base de datos)
        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);//guarda la publicacion en la base de datos. Hibernate/JPA se encarga de la insercion

        //convertimos Entidad a DTO
        PublicacionDTO publicacionDTOrespuesta = mapearDTO(nuevaPublicacion);

        return publicacionDTOrespuesta;
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int tamanoDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending() : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, tamanoDePagina, sort);
        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);//obtiene todas las publicaciones de la base de datos. Hibernate/JPA se encarga de la consulta

        List<Publicacion> listaDePublicaciones = publicaciones.getContent();

        List<PublicacionDTO> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());//mapea la lista de publicaciones a una lista de publicacionesDTO

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroDePagina(publicaciones.getNumber());
        publicacionRespuesta.setTamanoDePagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        Publicacion publicacion = publicacionRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
                    //obtiene la publicacion por id. Si no, manda excepcion. Hibernate

        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(Long id, PublicacionDTO publicacionDTO) {
        Publicacion publicacion = publicacionRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);//guarda la publicacion en la base de datos. Hibernate
        return mapearDTO(publicacionActualizada);

    }

    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));//esto busca la publicacion por id. Si no, manda excepcion. Hibernate

        publicacionRepository.delete(publicacion);//si hay respuesta, la encuentra, la eliminara. Hibernate
    }

    //------------------------------------------------------------------------------------------------
    //*********HECHO CON MODEL MAPPER*********
    private PublicacionDTO mapearDTO(Publicacion publicacion){//mapea una publicacion a una publicacionDTO
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);

        return publicacionDTO;
    }

    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){//mapea una publicacionDTO a una publicacion
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);//

        return publicacion;
    }

    //          *******HECHO A MANO********
    //convertir entidad(instancia de la clase) a DTO
    /*private PublicacionDTO mapearDTO(Publicacion publicacion){
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setTitulo(publicacion.getTitulo());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        publicacionDTO.setContenido(publicacion.getContenido());

        return publicacionDTO;
    }*/

    //convertir DTO a entidad(instancia de la clase)
//    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
//        Publicacion publicacion = new Publicacion();
//        //publicacion.setId(publicacionDTO.getId());
//        publicacion.setTitulo(publicacionDTO.getTitulo());
//        publicacion.setDescripcion(publicacionDTO.getDescripcion());
//        publicacion.setContenido(publicacionDTO.getContenido());
//
//        return publicacion;
//    }
}
