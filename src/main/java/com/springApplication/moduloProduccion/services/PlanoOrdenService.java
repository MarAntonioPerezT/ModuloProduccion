package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.PlanoOrden;
import com.springApplication.moduloProduccion.repositories.PlanoOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanoOrdenService {

    @Autowired
    private PlanoOrdenRepository repository;

    public void savePlanoOrden(PlanoOrden planoOrden){
        repository.save(planoOrden);
    }

    public PlanoOrden findPlanoByTituloArchivo(String titulo){
        return repository.findByNombrePlano(titulo);
    }
}
