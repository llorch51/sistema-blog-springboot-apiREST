package com.sistema.blog.dto;

import lombok.Getter;
import lombok.Setter;

public class RegistryDTO {

    @Getter @Setter
    private String nombre;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;

}
