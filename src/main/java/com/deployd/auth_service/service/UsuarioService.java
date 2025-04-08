package com.deployd.auth_service.service;

import com.deployd.auth_service.enums.Estado;
import com.deployd.auth_service.controller.AuthController;
import com.deployd.auth_service.dto.UsuarioDTO;
import com.deployd.auth_service.entity.Local;
import com.deployd.auth_service.entity.Rol;
import com.deployd.auth_service.entity.Usuario;
import com.deployd.auth_service.enums.RolNombre;
import com.deployd.auth_service.repository.LocalRepository;
import com.deployd.auth_service.repository.RolRepository;
import com.deployd.auth_service.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    LocalRepository localRepository;

    @Autowired
    RolRepository rolRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByUserName(nombreUsuario);
    }
/*
    public Optional<Usuario> getByNombreUsuarioOrEmail(String userNameOrEmail) {
        return usuarioRepository.findByUserName(userNameOrEmail);
    }
*/

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
/*
    public Usuario save(Usuario usuario) throws Exception {
        // Verificar si el usuario ya existe
        if (usuarioRepository.existsByUserName(usuario.getUserName())) {
            throw new Exception("El correo electrónico ya está registrado");
        }
        // Si no existe, se guarda el nuevo usuario y se retorna
        return usuarioRepository.save(usuario);
    }
*/

    public Usuario save(Usuario usuario) throws Exception {
        if (usuario.getId() == null || usuario.getId() == 0) {
            // Si el id es null o 0, significa que es un usuario nuevo
            if (usuarioRepository.existsByUserName(usuario.getUserName())) {
                // Si el correo ya está registrado y el usuario es nuevo, lanzamos la excepción
                throw new Exception("El correo electrónico ya está registrado");
            }
        }
        // Si no existe o si es un usuario ya existente, se guarda o actualiza
        return usuarioRepository.save(usuario);
    }


    
    public boolean existsById(Integer id) {
        return usuarioRepository.existsById(id); // Ya implementado por JpaRepository
    }

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

  /*  public boolean checkDniOrUsernameExists(String value, Long excludeUserId) {
        if (excludeUserId == null) {
            // Caso de creación: usa una consulta simplificada
            return usuarioRepository.existsByDniOrUsername(value, -1L); // Excluye un ID inexistente
        }
        return usuarioRepository.existsByDniOrUsername(value, excludeUserId);
    }*/

  public boolean validarDniUnico(String dni, Long excludeUserId) {
      if (excludeUserId == null) {
          // Creación de un nuevo usuario, solo valida si el DNI ya existe
          return usuarioRepository.existsByDni(dni);
      } else {
          // Actualización de un usuario, valida excluyendo el ID actual
          return usuarioRepository.existsByDniExcludingId(dni, excludeUserId);
      }
  }

    public boolean existsByUserNameExcludingId(String userName, Long excludeUserId) {
        if (excludeUserId == null) {
            // Creación de un nuevo usuario: valida si el nombre de usuario ya existe
            return usuarioRepository.existsByUserName(userName);
        } else {
            // Actualización de un usuario: valida excluyendo el ID actual
            return usuarioRepository.existsByUserNameExcludingId(userName, excludeUserId);
        }
    }



    public boolean existsByDni(String dni) {
        try {
            return usuarioRepository.existsByDni(dni);
        } catch (DataAccessException e) {
            // Manejar excepciones relacionadas con la base de datos
            System.err.println("Error al acceder a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al verificar si el dni existe.", e);
        } catch (Exception e) {
            // Manejar cualquier otra excepción no esperada
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al verificar si el dni existe.", e);
        }
    }


    public boolean existsByUserName(String userName) {
        try {
            return usuarioRepository.existsByUserName(userName);
        } catch (DataAccessException e) {
            // Manejar excepciones relacionadas con la base de datos
            System.err.println("Error al acceder a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al verificar si el usuario existe.", e);
        } catch (Exception e) {
            // Manejar cualquier otra excepción no esperada
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al verificar si el usuario existe.", e);
        }
    }


    public void deleteUsuario(int userId) {
        // Eliminar las relaciones en la tabla intermediaria
        usuarioRepository.deleteUserLocalAssociations(userId);

        // Ahora se puede eliminar el usuario
        usuarioRepository.deleteById(userId);
    }
/*
    public Usuario editarUsuario(Usuario usuarioModificado) {
        // Buscar el usuario por su ID
        Optional<Usuario> usuarioExistenteOptional = usuarioRepository.findById(usuarioModificado.getId());

        if (usuarioExistenteOptional.isPresent()) {
            Usuario usuario = usuarioExistenteOptional.get();

            // Actualizar campos básicos
            usuario.setNombres(usuarioModificado.getNombres());
            usuario.setDni(usuarioModificado.getDni());
            usuario.setApellidoPaterno(usuarioModificado.getApellidoPaterno());
            usuario.setApellidoMaterno(usuarioModificado.getApellidoMaterno());
            usuario.setCelular(usuarioModificado.getCelular());
            usuario.setDireccion(usuarioModificado.getDireccion());
            usuario.setNotas(usuarioModificado.getNotas());
            usuario.setUserName(usuarioModificado.getUserName());
            usuario.setPassword(usuarioModificado.getPassword());
            usuario.setActivo(usuarioModificado.isActivo());

            // Actualizar roles
            if (usuarioModificado.getRoles() != null) {
                Set<Rol> roles = usuarioModificado.getRoles().stream()
                        .map(rolNombre -> rolRepository.findByRolNombre(RolNombre.valueOf(rolNombre.toString()))
                                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + rolNombre)))
                        .collect(Collectors.toSet());
                usuario.setRoles(roles); // Cambiado a usuario
            }

            // Actualizar locales
            if (usuarioModificado.getLocales() != null) {
                Set<Local> locales = usuarioModificado.getLocales().stream()
                        .map(localId -> localRepository.findById(localId)
                                .orElseThrow(() -> new IllegalArgumentException("Local no encontrado con ID: " + localId)))
                        .collect(Collectors.toSet());
                usuario.setLocales(locales); // Cambiado a usuario
            }

            // Guardar el usuario actualizado
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado con el ID: " + usuarioModificado.getId());
        }
    }
*/

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }


    public Usuario actualizarUsuario(int id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        // Actualizar campos básicos
        usuarioExistente.setUserName(usuarioDTO.getUserName());
        usuarioExistente.setEstado(Estado.ACTIVO);
        usuarioExistente.setNombres(usuarioDTO.getNombres());
        usuarioExistente.setApellidos(usuarioDTO.getApellidos());
        usuarioExistente.setDni(usuarioDTO.getDni());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());

        // No actualizar la contraseña si no se proporciona un valor
        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            // Si el usuarioDTO tiene una contraseña nueva, actualízala
            usuarioExistente.setPassword(usuarioDTO.getPassword()); // O usar algún método para encriptarla
        }

        // Actualizar roles
        if (usuarioDTO.getRoles() != null) {
            Set<Rol> roles = usuarioDTO.getRoles().stream()
                    .map(rolNombre -> rolRepository.findByRolNombre(RolNombre.valueOf(rolNombre))
                            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + rolNombre)))
                    .collect(Collectors.toSet());
            usuarioExistente.setRoles(roles);
        }

        // Actualizar locales
        if (usuarioDTO.getLocales() != null) {
            Set<Local> locales = usuarioDTO.getLocales().stream()
                    .map(localId -> localRepository.findById(localId)
                            .orElseThrow(() -> new IllegalArgumentException("Local no encontrado con ID: " + localId)))
                    .collect(Collectors.toSet());
            usuarioExistente.setLocales(locales);
        }

        // Guardar cambios
        return usuarioRepository.save(usuarioExistente);
    }

    public Optional<Usuario> getByTokenPassword(String tokenpassword){
        return usuarioRepository.findByTokenPassword(tokenpassword);
    }

}
