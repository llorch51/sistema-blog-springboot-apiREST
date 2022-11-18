package com.sistema.blog.controller;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.entity.Publicacion;
import com.sistema.blog.service.PublicacionService;
import com.sistema.blog.utillities.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {//metodos que implementaran los servicios de PublicacionServiceImpl

    @Autowired
    private PublicacionService publicacionService;//crear una instancia de la interfaz
    //------------------------------------------------------------------------------------------

    @GetMapping//pagination. Se le pasa el numero de pagina y el numero de elementos por pagina
    public PublicacionRespuesta listarPublicaciones(
            @RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false )int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConstantes.TAMANO_DE_PAGINA_POR_DEFECTO, required = false)int tamanoDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_POR_DEFECTO, required = false)String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.SORT_DIR_POR_DEFECTO, required = false)String sortDir){
        return publicacionService.obtenerTodasLasPublicaciones(numeroDePagina, tamanoDePagina, ordenarPor, sortDir);
    }

    @GetMapping("/{id}")//obtener una publicacion por su id
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
    }

    @PostMapping//crear una publicacion
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionService.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")//modificara una publicacion
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@PathVariable(name = "id") Long id, @RequestBody PublicacionDTO publicacionDTO){
        PublicacionDTO publicacionRespuesta = publicacionService.actualizarPublicacion(id, publicacionDTO);
        return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")//eliminara una publicacion
    public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") Long id){
        publicacionService.eliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
    }

}
