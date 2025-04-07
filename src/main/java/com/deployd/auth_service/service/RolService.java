package com.deployd.auth_service.service;


import com.deployd.auth_service.entity.Rol;
import com.deployd.auth_service.enums.RolNombre;
import com.deployd.auth_service.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> buscarporNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void guardar(Rol rol){
        rolRepository.save(rol);
    }

    public void eliminar(int id) {
        // TODO Auto-generated method stub
    }

    public Rol buscar(int id) {
        return null;
    }

    public List<Rol> buscarTodos() {
        return rolRepository.findAll();
    }




}
