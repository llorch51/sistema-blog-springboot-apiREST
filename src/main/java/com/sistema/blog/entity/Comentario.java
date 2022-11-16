package com.sistema.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
@NoArgsConstructor
public class Comentario {//Crear la tabla comentarios en la base de datos y hara referencia a la tabla publicaciones con la clave foranea publicacion_id

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String contenido;

    @ManyToOne(fetch = FetchType.LAZY)//.LAZY - solo se cargara cuando se llame a la publicacion
    @JoinColumn(name = "publicacion_id", nullable = false)//nombre de la columna en la tabla comentarios
    @Getter @Setter
    private Publicacion publicacion;
}
