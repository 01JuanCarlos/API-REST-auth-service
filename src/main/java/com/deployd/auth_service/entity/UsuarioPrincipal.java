package com.deployd.auth_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deployd.auth_service.enums.Estado;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//maneja los privilegios de cada usuario
public class UsuarioPrincipal implements UserDetails {

    @NotBlank
    @Size(max = 50)
    private String userName; //es el correo usado para autenticación.
    @NotBlank
    @Size(min = 4, max = 255)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;    
    @NotBlank
    @Size(min = 3, max = 50)
    private String nombres;
    @NotBlank
    @Size(min = 3, max = 30)
    private String apellidos;
    @NotBlank
    @Size(min = 8, max = 8)
    private String dni;
    @Size(max = 30)
    private String telefono;
  
    private Collection<? extends GrantedAuthority> authorities;

    
	public UsuarioPrincipal(@NotBlank @Size(max = 50) String userName,
			@NotBlank @Size(min = 4, max = 255) String password, Estado estado,
			@NotBlank @Size(min = 3, max = 50) String nombres, @NotBlank @Size(min = 3, max = 30) String apellidos,
			@NotBlank @Size(min = 8, max = 8) String dni, @Size(max = 30) String telefono,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userName = userName;
		this.password = password;
		this.estado = estado;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		this.authorities = authorities;
	}

	public UsuarioPrincipal() {
		super();
	}

	public static UsuarioPrincipal build(Usuario usuario) {
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()))
                .collect(Collectors.toList());

        return new UsuarioPrincipal(
                usuario.getUserName(),
                usuario.getPassword(),
                usuario.getEstado(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getDni(),
                usuario.getTelefono(),
                authorities
        );
    }



	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @NotBlank @Size(min = 4, max = 255) String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //get and set

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}



}
