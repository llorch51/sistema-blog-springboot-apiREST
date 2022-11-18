package com.sistema.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SistemaBlogSpringbootApiRestApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();//ModelMapper es una librería que nos permite mapear objetos de una clase a otra.
        //Es decir, podemos convertir un objeto de una clase a otro objeto de otra clase.
        //En este caso, vamos a utilizar ModelMapper para convertir los objetos de la clase Comentario a ComentarioDTO y viceversa.
        //Para ello, vamos a crear dos métodos en la clase ComentarioServiceImpl, uno para convertir un Comentario a ComentarioDTO y otro para convertir un ComentarioDTO a Comentario.
    }

    public static void main(String[] args) {
        SpringApplication.run(SistemaBlogSpringbootApiRestApplication.class, args);
    }

}
