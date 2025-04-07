package com.deployd.auth_service.entity;

import com.deployd.auth_service.enums.Estado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data // Genera automáticamente Getters, Setters, toString(), equals(), hashCode()
@AllArgsConstructor // Genera un constructor con todos los atributos
@NoArgsConstructor // Genera un constructor vacío
@Entity
@Table(name = "user")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @Size(max = 50)
  //  @Column(unique = true)
    private String userName; //es el correo usado para autenticación.
    @NotBlank
    @Size(min = 4, max = 255)
    private String password;

    private String tokenpassword;

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.ACTIVO;

    
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

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_local",
            joinColumns = @JoinColumn(name = "user_id"), // id de entidad Usuario.
            inverseJoinColumns = @JoinColumn(name = "local_id")) // id de entidad Local.
    @JsonProperty(value = "locales")
    private Set<Local> locales;


	public Usuario( @NotBlank @Size(max = 50) String userName,
			@NotBlank @Size(min = 4, max = 255) String password, Estado estado,
			@NotBlank @Size(min = 3, max = 50) String nombres, @NotBlank @Size(min = 3, max = 30) String apellidos,
			@NotBlank @Size(min = 8, max = 8) String dni, @Size(max = 30) String telefono) {
		this.userName = userName;
		this.password = password;
		this.estado = estado;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
	}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank @Size(max = 50) String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank @Size(max = 50) String userName) {
        this.userName = userName;
    }

    public @NotBlank @Size(min = 4, max = 255) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 4, max = 255) String password) {
        this.password = password;
    }

    public String getTokenpassword() {
        return tokenpassword;
    }

    public void setTokenpassword(String tokenpassword) {
        this.tokenpassword = tokenpassword;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public @NotBlank @Size(min = 3, max = 50) String getNombres() {
        return nombres;
    }

    public void setNombres(@NotBlank @Size(min = 3, max = 50) String nombres) {
        this.nombres = nombres;
    }

    public @NotBlank @Size(min = 3, max = 30) String getApellidos() {
        return apellidos;
    }

    public void setApellidos(@NotBlank @Size(min = 3, max = 30) String apellidos) {
        this.apellidos = apellidos;
    }

    public @NotBlank @Size(min = 8, max = 8) String getDni() {
        return dni;
    }

    public void setDni(@NotBlank @Size(min = 8, max = 8) String dni) {
        this.dni = dni;
    }

    public @Size(max = 30) String getTelefono() {
        return telefono;
    }

    public void setTelefono(@Size(max = 30) String telefono) {
        this.telefono = telefono;
    }

    public @NotNull Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(@NotNull Set<Rol> roles) {
        this.roles = roles;
    }

    public Set<Local> getLocales() {
        return locales;
    }

    public void setLocales(Set<Local> locales) {
        this.locales = locales;
    }
}
