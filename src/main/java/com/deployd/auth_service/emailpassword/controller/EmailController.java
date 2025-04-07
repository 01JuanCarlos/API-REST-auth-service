package com.deployd.auth_service.emailpassword.controller;


import com.deployd.auth_service.dto.Mensaje;
import com.deployd.auth_service.emailpassword.dto.EmailValuesDTO;
import com.deployd.auth_service.emailpassword.service.EmailService;
import com.deployd.auth_service.entity.Usuario;
import com.deployd.auth_service.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UsuarioService usuarioService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private static final String subject ="Cambio de contraseña";

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioService.getByNombreUsuario(dto.getMailTo());
        if(!usuarioOpt.isPresent()){
            return new ResponseEntity<>(new Mensaje("No existe ni un usuario con esas credenciales"),HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioOpt.get();
        dto.setMailFrom(mailFrom);
        dto.setMailTo(usuario.getUserName());
        dto.setSubject(subject);
        dto.setUserName(usuario.getUserName());
        UUID uuid= UUID.randomUUID();
        String tokemPassword = uuid.toString();
        dto.setTokenPassword(tokemPassword);
        usuario.setTokenpassword(tokemPassword);
        try {
            usuarioService.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        emailService.sendEmail(dto);
        return new ResponseEntity(new Mensaje("Te hemos enviado un correo"), HttpStatus.OK);
    }
}