package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.PersonasMoralesCliente;
import com.springApplication.moduloProduccion.repositories.PersonasMoralesClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class PersonasMoralesClientesService implements CrudListener<PersonasMoralesCliente> {

    @Autowired
    private PersonasMoralesClientesRepository repository;

    public PersonasMoralesClientesService(PersonasMoralesClientesRepository repository){
        this.repository = repository;
    }

    @Override
    public Collection<PersonasMoralesCliente> findAll() {
        return repository.findAll();
    }

    @Override
    public PersonasMoralesCliente add(PersonasMoralesCliente personasMoralesCliente) {
        return repository.save(personasMoralesCliente);
    }

    @Override
    public PersonasMoralesCliente update(PersonasMoralesCliente personasMoralesCliente) {
        return repository.save(personasMoralesCliente);
    }

    @Override
    public void delete(PersonasMoralesCliente personasMoralesCliente) {
        repository.delete(personasMoralesCliente);
    }

    public String findRazonSocialByRFC(String razonSocial){
        return repository.findRazonSocialByRFC(razonSocial);
    }
}
