package com.deployd.auth_service.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.deployd.auth_service.dto.Mensaje;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

   @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        try {
            logger.error("fail en el método commence: {}", authException.getMessage());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Establece el código de estado 401
            // Crea el objeto Mensaje con el texto del error
            Mensaje mensaje = new Mensaje("Credenciales incorrectas");
            // Convierte el objeto Mensaje a JSON y lo escribe en la respuesta
            String jsonResponse = new ObjectMapper().writeValueAsString(mensaje);
            res.getWriter().write(jsonResponse);
        } catch (IOException e) {
            logger.error("Error al enviar la respuesta de error: {}", e.getMessage());
            throw new ServletException("Error interno del servidor", e); // O maneja según tu lógica
        }
    }


  /*  @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
       try {
            // Registrar el error de autenticación
            logger.error("Error de autenticación: {}", authException.getMessage());

            // Establecer el código de estado HTTP 401 (No Autorizado)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Crear un mensaje de error genérico para la autenticación fallida
            String errorMessage = "Acceso no autorizado";

            // Enviar el mensaje de error como respuesta
            response.getWriter().write("{\"message\": \"" + errorMessage + "\"}");
        } catch (IOException e) {
            logger.error("Error al enviar la respuesta de error: {}", e.getMessage());
            throw new ServletException("Error interno del servidor", e);
        }
    }
*/
}
