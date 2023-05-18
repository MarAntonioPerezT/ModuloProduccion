package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Clientes;
import com.springApplication.moduloProduccion.repositories.ClientesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ClientesService implements CrudListener<Clientes> {

    @Autowired
    private ClientesRepository repository;

    public ClientesService(ClientesRepository repository) {
        this.repository = repository;

    }

    @Override
    public Collection<Clientes> findAll() {
        return repository.findAll();
    }

    @Override
    public Clientes add(Clientes clientes) {
        return repository.save(clientes);
    }

    @Override
    public Clientes update(Clientes clientes) {
        return repository.save(clientes);
    }

    @Override
    public void delete(Clientes clientes) {
        repository.delete(clientes);
    }

    public void saveAllClientes(List<Clientes> clientes){
        repository.saveAll(clientes);
    }

    public List<String> obtenerNombresClientes(){
        return repository.obtenerNombresClientes();
    }

    public Long count(){
        return repository.count();
    }

    public Long obtenerClientePorIdPersona(Long idPersonaCliente){
        return repository.obtenerClientePorIdPersona(idPersonaCliente);
    }

    public Long obtenerIdClientePorRFC(String rfc){
        return repository.obtenerIdClientePorRFC(rfc);
    }

    public Clientes obtenerClientePorId(Long id){
        return repository.findByIdCliente(id);
    }
}
