package com.springApplication.moduloProduccion.services;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.springApplication.moduloProduccion.models.Materiales;
import com.springApplication.moduloProduccion.repositories.MaterialesRespository;

@Service
public class MaterialesService implements CrudListener<Materiales>{
    
    public MaterialesRespository repository;

    public MaterialesService(MaterialesRespository repository){

        this.repository = repository;
    }

    @Override
    public Materiales add(Materiales material) {
        // TODO Auto-generated method stub
        return repository.save(material);
    }

    @Override
    public void delete(Materiales material) {
        // TODO Auto-generated method stub
        repository.delete(material); 
    }

    public Collection<Materiales> findAllMateriales(String filterText) {
        // TODO Auto-generated method stub
        if(filterText == null ||  filterText.isEmpty()){
            return repository.findAll(); 
        }else{
            return repository.search(filterText);
        }
    }

    @Override
    public Materiales update(Materiales material) {
        // TODO Auto-generated method stub
        return repository.save(material);
    }

    @Override
    public Collection<Materiales> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    public void saveAllMateriales(List<Materiales> materiales){
        repository.saveAll(materiales);
    }
   
}
