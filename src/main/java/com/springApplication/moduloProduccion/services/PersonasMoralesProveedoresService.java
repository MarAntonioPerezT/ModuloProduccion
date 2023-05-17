package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.PersonasMoralesProveedor;
import com.springApplication.moduloProduccion.repositories.PersonasMoralesProveedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class PersonasMoralesProveedoresService implements CrudListener<PersonasMoralesProveedor> {

    @Autowired
    private PersonasMoralesProveedoresRepository repository;

    public PersonasMoralesProveedoresService(PersonasMoralesProveedoresRepository repository){
        this.repository = repository;
    }

    @Override
    public Collection<PersonasMoralesProveedor> findAll() {
        return repository.findAll();
    }

    @Override
    public PersonasMoralesProveedor add(PersonasMoralesProveedor personasMoralesProveedor) {
        return repository.save(personasMoralesProveedor);
    }

    @Override
    public PersonasMoralesProveedor update(PersonasMoralesProveedor personasMoralesProveedor) {
        return repository.save(personasMoralesProveedor);
    }

    @Override
    public void delete(PersonasMoralesProveedor personasMoralesProveedor) {
        repository.delete(personasMoralesProveedor);
    }

    public String findRazonSocialProveedorByRFC(String rfc){
        return repository.findRazonSocialByRFC(rfc);
    }

    public Long count(){
        return repository.count();
    }
}
