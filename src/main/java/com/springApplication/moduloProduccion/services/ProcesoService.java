package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Proceso;
import com.springApplication.moduloProduccion.repositories.ProcesoRepository;
import com.vaadin.flow.component.textfield.IntegerField;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcesoService {

    @Autowired
    private ProcesoRepository procesoRepository;

    public ProcesoService(ProcesoRepository procesoRepository){

        this.procesoRepository = procesoRepository;
    }

    public void saveProceso(Proceso proceso){

        procesoRepository.save(proceso);

    }

    public List<Proceso> getAllProcesos(){

        return procesoRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoProceso"));

    }

    public void removeById(Proceso proceso){

        procesoRepository.deleteById(proceso.getCodigo_proceso());

    }

    public Proceso getProcesoByNombre(String nombre){

        return procesoRepository.findByNombre(nombre);
    }

    public Long getCountProceso(){

        return procesoRepository.count();

    }

    public void borrarProceso(Proceso proceso){

        procesoRepository.delete(proceso);
    }

    public List<String> getAllProcesosByNombre(){

        return procesoRepository.getAllProcesosByName();
    }

}
