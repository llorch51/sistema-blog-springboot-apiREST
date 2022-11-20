package com.sistema.blog.security;

import com.sistema.blog.exceptions.BlogAppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {//clase que nos permite generar el token. Esta clase es la que se encarga de generar el token y de validar el token

    @Value("${app.jwt-secret}")//se obtiene el valor de la propiedad app.jwt.secret del archivo application.properties
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {//se genera el token con su llave secretaria y su tiempo de expiracion
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);//se le suma la fecha actual con la fecha de expiracion

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)//mientras mejor sea la codificacion 'HS256', requiere de un jwtsecret mas largo
                .compact();//se contruye el token con todos esos datos
        return token;
    }

    public String getUserNameFromJWT(String token) {//se obtiene el nombre de usuario del token
        Claims claims = Jwts.parser()//claims es un objeto que contiene los datos del token.
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();//se obtiene el nombre del usuario
    }

    public boolean validateToken(String token) {//se valida el token
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);//se valida el token con su llave secreta
            return true;
        } catch (SignatureException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Firma JWT no valida");
        }catch (MalformedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no valido");
        }catch (ExpiredJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT expirado");
        }catch (UnsupportedJwtException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no soportado");
        }catch (IllegalArgumentException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "JWT claims string vacio");//datos del token vacios
        }
    }
}
