package com.deployd.auth_service.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Local")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String username;

    // Relación bidireccional con Usuario (ManyToMany)
    @ManyToMany(mappedBy = "locales")
    private Set<Usuario> usuarios;


    public Local() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

  public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
