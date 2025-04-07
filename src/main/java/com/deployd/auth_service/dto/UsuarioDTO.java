package com.deployd.auth_service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.deployd.auth_service.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class UsuarioDTO {

    @NotBlank
    @Size(max = 50)
    private String userName; // Es el correo usado para autenticación.

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

   
    private Set<String> roles = new HashSet<>(); // Nombres de roles (en lugar de IDs)

    private List<Integer> locales; // IDs de locales

 

    public UsuarioDTO() {

    }


    // Getters y setters
	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
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



	public Set<String> getRoles() {
		return roles;
	}



	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}



	public List<Integer> getLocales() {
		return locales;
	}



	public void setLocales(List<Integer> locales) {
		this.locales = locales;
	}
    
   


}
