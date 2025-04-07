package com.deployd.auth_service.enums;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum RolNombre {
    ADMIN,RECEPCION;

    @JsonCreator
    public static RolNombre fromString(String rol) {
        return RolNombre.valueOf(rol.toUpperCase());  // Convierte la cadena a mayúsculas antes de buscarla
    }
}
