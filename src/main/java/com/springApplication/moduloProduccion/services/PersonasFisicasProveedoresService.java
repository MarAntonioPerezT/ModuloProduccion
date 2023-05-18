package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.PersonasFisicasProveedor;
import com.springApplication.moduloProduccion.repositories.PersonasFisicasProveedoresRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.Crud;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class PersonasFisicasProveedoresService implements CrudListener<PersonasFisicasProveedor> {

    @Autowired
    private PersonasFisicasProveedoresRepository repository;

    public PersonasFisicasProveedoresService(PersonasFisicasProveedoresRepository repository){
        this.repository = repository;
    }

    @Override
    public Collection<PersonasFisicasProveedor> findAll() {
        return repository.findAll();
    }

    @Override
    public PersonasFisicasProveedor add(PersonasFisicasProveedor personasFisicasProveedor) {
        return repository.save(personasFisicasProveedor);
    }

    @Override
    public PersonasFisicasProveedor update(PersonasFisicasProveedor personasFisicasProveedor) {
        return repository.save(personasFisicasProveedor);
    }

    @Override
    public void delete(PersonasFisicasProveedor personasFisicasProveedor) {
        repository.delete(personasFisicasProveedor);
    }

    public Long count(){
        return repository.count();
    }

    public String findNombreProveedorByRFC(String rfc){
        return repository.findNombreProveedorByRFC(rfc);
    }
}
