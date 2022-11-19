package com.sistema.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role")
@NoArgsConstructor
public class Role {//relacion many to many. Un usuario puede tener muchos roles y un rol puede tener muchos usuarios
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(length = 20)
    @Getter @Setter
    private String name;
}
