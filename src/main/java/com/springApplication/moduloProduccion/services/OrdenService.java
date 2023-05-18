package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Orden;
import com.springApplication.moduloProduccion.repositories.OrdenRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
public class OrdenService implements CrudListener<Orden> {

    @Override
    public Collection<Orden> findAll() {
        return repository.findAll();
    }

    @Override
    public Orden add(Orden orden) {
        return repository.save(orden);
    }

    @Override
    public Orden update(Orden orden) {
        return repository.save(orden);
    }

    @Override
    public void delete(Orden orden) {
        repository.delete(orden);
    }

    private OrdenRepository repository;

    public OrdenService(OrdenRepository repository){
        this.repository = repository;
    }


    public Long contarOrdenes(){
        return repository.count();
    }

    public void guardarOrden(Orden orden){
        repository.save(orden);
    }
}
