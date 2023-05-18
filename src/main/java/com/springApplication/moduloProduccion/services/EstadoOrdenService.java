package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.EstadoOrden;
import com.springApplication.moduloProduccion.repositories.EstadoOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoOrdenService {

    @Autowired
    private EstadoOrdenRepository repository;

    public EstadoOrdenService(EstadoOrdenRepository repository){
        this.repository = repository;
    }

    public String findEstatusNombreByNombre(String nombre){
        return repository.findEstatusNombreByNombre(nombre);
    }

    public EstadoOrden findEstatusByNombreEstado(String estado){
        return repository.findByEstado(estado);
    }

    public Long count(){
        return repository.count();
    }

    public void saveEstadoOrden(EstadoOrden estadoOrden){
        repository.save(estadoOrden);
    }

}
