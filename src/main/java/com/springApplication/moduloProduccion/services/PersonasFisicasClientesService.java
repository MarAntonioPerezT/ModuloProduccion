package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.PersonasFisicasCliente;
import com.springApplication.moduloProduccion.repositories.PersonasFisicasClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class PersonasFisicasClientesService implements CrudListener<PersonasFisicasCliente> {

    @Autowired
    private PersonasFisicasClientesRepository repository;

    public PersonasFisicasClientesService(PersonasFisicasClientesRepository repository){
        this.repository = repository;
    }

    @Override
    public Collection<PersonasFisicasCliente> findAll() {
        return repository.findAll();
    }

    @Override
    public PersonasFisicasCliente add(PersonasFisicasCliente personasFisicasCliente) {
        return repository.save(personasFisicasCliente);
    }

    @Override
    public PersonasFisicasCliente update(PersonasFisicasCliente personasFisicasCliente) {
        return repository.save(personasFisicasCliente);
    }

    @Override
    public void delete(PersonasFisicasCliente personasFisicasCliente) {
        repository.delete(personasFisicasCliente);
    }

    public String findNombreClienteByRFC(String rfc){
        return repository.findNombreClienteByRFC(rfc);
    }
}
