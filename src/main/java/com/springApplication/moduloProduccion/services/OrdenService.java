package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Orden;
import com.springApplication.moduloProduccion.repositories.OrdenRepository;
import org.springframework.stereotype.Service;

@Service
public class OrdenService {

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
