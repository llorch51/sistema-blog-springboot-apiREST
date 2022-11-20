package com.sistema.blog.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponseDTO {

    @Getter
    @Setter
    private String accessToken;
    @Getter
    @Setter
    private String tokenType = "Bearer";

    public JWTAuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
