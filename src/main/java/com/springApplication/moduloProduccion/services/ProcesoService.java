package com.springApplication.moduloProduccion.services;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.springApplication.moduloProduccion.models.Procesos;
import com.springApplication.moduloProduccion.repositories.ProcesoRepository;

@Service
public class ProcesoService implements CrudListener<Procesos>{

    public ProcesoRepository repository;

    public ProcesoService(ProcesoRepository repository){

        this.repository = repository;
    }

    @Override
    public Procesos add(Procesos proceso) {
        // TODO Auto-generated method stub
        return repository.save(proceso);
    }

    @Override
    public void delete(Procesos proceso) {
        // TODO Auto-generated method stub
        repository.delete(proceso); 
    }

    public Collection<Procesos> findAllProcesos(String filterText) {
        // TODO Auto-generated method stub
        if(filterText == null ||  filterText.isEmpty()){
            return repository.findAll(); 
        }else{
            return repository.search(filterText);
        }
    }

    @Override
    public Procesos update(Procesos proceso) {
        // TODO Auto-generated method stub
        return repository.save(proceso);
    }

    @Override
    public Collection<Procesos> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    public void saveAllProcesos(List<Procesos> procesos){
        repository.saveAll(procesos);
    }

    public List<String> findAllProcesosByNombre(){
        return repository.findAllProcesosByNombre();
    }

    public Long count(){
        return repository.count();
    }

    public Procesos findByNombre(String nombre){
        return repository.findByNombre(nombre);
    }
   

}
