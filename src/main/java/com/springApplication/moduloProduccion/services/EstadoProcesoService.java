package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.EstadoProceso;
import com.springApplication.moduloProduccion.repositories.EstadoOrdenRepository;
import com.springApplication.moduloProduccion.repositories.EstadoProcesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoProcesoService {

    @Autowired
    private EstadoProcesoRepository estadoProcesoRepository;

    public EstadoProcesoService(EstadoProcesoRepository estadoProcesoRepository){
        this.estadoProcesoRepository = estadoProcesoRepository;
    }

    public EstadoProceso findByEstado(String estado){
        return estadoProcesoRepository.findByNombre(estado);
    }

    public void saveEstadoProceso(EstadoProceso estadoProceso){
        estadoProcesoRepository.save(estadoProceso);
    }

}
