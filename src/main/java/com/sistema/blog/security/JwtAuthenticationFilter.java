package com.sistema.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws javax.servlet.ServletException, java.io.IOException {
        //obtenemos el token de la solicitud http
        String token = getJwtFromRequest(request);
        //validamos el token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            //obtenemos el username del token
            String username = tokenProvider.getUserNameFromJWT(token);
            //cargamos el usuario asociado al token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //establecems la seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //validar el filtro
        filterChain.doFilter(request, response);
    }

    //bearer token de acceso
    private String getJwtFromRequest(javax.servlet.http.HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());//le estamos quitando el 'bearer' del token
        }
        return null;
    }

}
