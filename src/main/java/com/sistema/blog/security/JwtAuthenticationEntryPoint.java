package com.sistema.blog.security;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws java.io.IOException, javax.servlet.ServletException {
        response.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }


}
