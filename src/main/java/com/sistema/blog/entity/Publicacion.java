package com.sistema.blog.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity                         //Uniqueconstraints - no podra haber una publicacion con titulo repetido
@Table(name = "publicaciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})//name=nombre de la tabla en la base de datos
@AllArgsConstructor @NoArgsConstructor//constructor con todos los campos //constructor sin ningun campo
public class Publicacion {

    @Getter @Setter @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter @Column (name = "titulo", nullable = false)
    private String titulo;
    @Getter @Setter @Column (name = "descripcion", nullable = false)
    private String descripcion;
    @Getter @Setter @Column (name = "contenido", nullable = false)
    private String contenido;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)//.ALL - cuando se borre una publicacion se borraran todos los comentarios asociados a ella
    private Set<Comentario> cometarios = new HashSet<>();//Set - no se pueden repetir los comentarios

}
