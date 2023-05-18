package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.PersonaProveedor;
import com.springApplication.moduloProduccion.repositories.PersonaProveedorRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaProveedorService{

    @Autowired
    private PersonaProveedorRepositry repositry;

    public PersonaProveedorService(PersonaProveedorRepositry repositry){
        this.repositry = repositry;
    }

    public void save(PersonaProveedor personaProveedor){
        repositry.save(personaProveedor);
    }

    public List<String> findAllProveedoresByRFC(){
        return repositry.findAllProveedoresByRFC();
    }

    public PersonaProveedor findPersonaProveedorByRFC(String rfc){
        return repositry.findByRfc(rfc);
    }

}
