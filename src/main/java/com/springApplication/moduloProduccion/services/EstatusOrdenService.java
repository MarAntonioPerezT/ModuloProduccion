package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.EstatusOrden;
import com.springApplication.moduloProduccion.repositories.EstatusOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstatusOrdenService {

    @Autowired
    private EstatusOrdenRepository estatusOrdenRepository;

    public EstatusOrdenService(EstatusOrdenRepository estatusOrdenRepository){
        this.estatusOrdenRepository = estatusOrdenRepository;
    }

    public String findEstatusNombreByNombre(String nombre){
        return estatusOrdenRepository.findEstatusNombreByNombre(nombre);
    }

    public EstatusOrden findEstatusByNombreEstado(String estado){
        return estatusOrdenRepository.findByEstado(estado);
    }
}
