package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Orden;
import com.springApplication.moduloProduccion.repositories.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;

    public OrdenService (OrdenRepository ordenRepository){
        this.ordenRepository = ordenRepository;
    }

    public Long contarOrdenes(){
        return ordenRepository.count();
    }

    public void guardarOrden(Orden orden){
        ordenRepository.save(orden);
    }

}
