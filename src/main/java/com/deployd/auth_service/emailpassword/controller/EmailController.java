package com.deployd.auth_service.emailpassword.controller;


import com.deployd.auth_service.dto.Mensaje;
import com.deployd.auth_service.emailpassword.dto.EmailValuesDTO;
import com.deployd.auth_service.emailpassword.dto.changePasswordDTO;
import com.deployd.auth_service.emailpassword.service.EmailService;
import com.deployd.auth_service.entity.Usuario;
import com.deployd.auth_service.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private static final String subject ="Cambio de contraseña";
/*
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
*/
    
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioService.getByNombreUsuario(dto.getMailTo());
        if (!usuarioOpt.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No existe ningún usuario con ese correo"), HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioOpt.get();

        // Preparar datos del DTO para el correo
        dto.setMailFrom(mailFrom);
        dto.setMailTo(usuario.getUserName()); // si userName es el correo
        dto.setSubject(subject);
        dto.setUserName(usuario.getUserName());

        // Generar y guardar token
        String tokenPassword = UUID.randomUUID().toString();
        dto.setTokenPassword(tokenPassword);
        usuario.setTokenpassword(tokenPassword);

        try {
            usuarioService.save(usuario);
            emailService.sendEmail(dto);
            return new ResponseEntity<>(new Mensaje("Te hemos enviado un correo"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje("Error al enviar el correo: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody changePasswordDTO dto , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  new ResponseEntity<>(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
        if(!dto.getPassword().equals(dto.getConfirmarPassword())){
            return new ResponseEntity(new Mensaje("Las contraseñas no coincide"),HttpStatus.BAD_REQUEST);
        }
        Optional<Usuario> usuarioOpt = usuarioService.getByTokenPassword(dto.getTokenPassword());
        if(!usuarioOpt.isPresent()){
            return new ResponseEntity<>(new Mensaje("No existe ni un usuario con esas credenciales"),HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioOpt.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        usuario.setPassword(newPassword);
        usuario.setTokenpassword(null);
        try {
            usuarioService.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
    }
}