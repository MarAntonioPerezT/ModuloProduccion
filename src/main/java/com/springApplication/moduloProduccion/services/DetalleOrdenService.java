package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.DetalleOrdenes;
import com.springApplication.moduloProduccion.repositories.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository repository;

    public DetalleOrdenService(DetalleOrdenRepository repository){
        this.repository = repository;
    }

    public void saveDetalleOrden(DetalleOrdenes detalleOrdenes){
        repository.save(detalleOrdenes);
    }

    public void saveAllDetalleOrden(List<DetalleOrdenes> detalleOrdenes){
        repository.saveAll(detalleOrdenes);
    }

}
