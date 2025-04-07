package com.deployd.auth_service.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtTokentFilter extends OncePerRequestFilter {


    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsService userDetailsService;
/*
   @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Obtener el token de la cabecera de la solicitud
            String token = getToken(req);

            // Si el token no es nulo y es válido
            if (token != null && jwtProvider.validateToken(token)) {
                // Obtener el nombre de usuario desde el token
                String nombreUsuario = jwtProvider.getUsernameFromToken(token);

                System.out.println("Nombre de usuario: "+nombreUsuario);

                // Cargar los detalles del usuario a partir del nombre de usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                // Crear el objeto de autenticación con los detalles del usuario y sus roles
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtException e) {
            // Captura excepciones específicas de JWT y maneja la respuesta de error adecuadamente
            logger.error("Error en el procesamiento del token JWT: {}", e.getMessage());
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado.");
            return; // Interrumpe la cadena de filtros
        } catch (AuthenticationException e) {
            // Captura errores de autenticación si ocurren
            logger.error("Error de autenticación: {}", e.getMessage());
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado.");
            return; // Interrumpe la cadena de filtros
        } catch (Exception e) {
            // Loguea errores genéricos
            logger.error("Error en el filtro JWT: ", e);
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(req, res);
    }
*/

/* //opcion2
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if (token != null && jwtProvider.validateToken(token)) {
            String username = jwtProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        try {
            filterChain.doFilter(request, response);
        } catch (JwtException jwtException) {
            // Manejo específico para problemas de JWT (token inválido o expirado)
            logger.error("Excepción JWT: {}", jwtException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido o expirado");
        }
    }
*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && jwtProvider.validateToken(token)) {
            Authentication authentication = jwtProvider.getAuthentication(token);  // Aquí llamamos al método getAuthentication
            if (authentication != null) {
                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);  // Continúa con la cadena de filtros
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Extrae el token sin el prefijo "Bearer "
        }
        return null;
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7).trim(); // Eliminar "Bearer " y los posibles espacios
        }
        return null;
    }

}
