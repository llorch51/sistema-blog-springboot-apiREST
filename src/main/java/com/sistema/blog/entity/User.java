package com.sistema.blog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}), @UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))//creara una tabla intermedia llamada usuarios_roles.
                                                    // En la tabla usuarios_roles tendra una columna llamada usuario_id y otra llamada role_id.
                                                    //ambas columnas seran llaves foraneas que apuntaran a la tabla usuarios y a la tabla roles respectivamente
    @Getter @Setter
    private Set<Role> roles = new HashSet<>();

}
