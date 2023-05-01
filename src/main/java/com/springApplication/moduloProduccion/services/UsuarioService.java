package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Usuario;
import com.springApplication.moduloProduccion.repositories.UsuarioReposiory;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioReposiory usuarioReposiory;

    public UsuarioService(UsuarioReposiory usuarioReposiory){
        this.usuarioReposiory = usuarioReposiory;
    }

    public String findNombreUsuarioByNombre(String nombre){
        return usuarioReposiory.findUsuarioNameByNombre(nombre);
    }

    public Usuario findUsuarioByNombre(String nombre){ return usuarioReposiory.findByNombre(nombre);}

}
