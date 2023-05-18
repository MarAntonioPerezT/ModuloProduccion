package com.springApplication.moduloProduccion.services;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import com.springApplication.moduloProduccion.models.Productos;
import com.springApplication.moduloProduccion.repositories.ProductosRepository;

@Service
public class ProductosService implements CrudListener<Productos>{

    ProductosRepository repository;

    public ProductosService(ProductosRepository repository){

        this.repository = repository;
    }

    @Override
    public Productos add(Productos proceso) {
        // TODO Auto-generated method stub
        return repository.save(proceso);
    }

    @Override
    public void delete(Productos proceso) {
        // TODO Auto-generated method stub
        repository.delete(proceso); 
    }

    public Collection<Productos> findAllProductos(String filterText) {
        // TODO Auto-generated method stub
        if(filterText == null ||  filterText.isEmpty()){
            return repository.findAll(); 
        }else{
            return repository.search(filterText);
        }
    }

    @Override
    public Productos update(Productos proceso) {
        // TODO Auto-generated method stub
        return repository.save(proceso);
    }

    @Override
    public Collection<Productos> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    public void saveAllProductos(List<Productos> productos){
        repository.saveAll(productos);
    }

    public List<Productos> findAllProductos(){
        return repository.findAll();
    }

    public Long countProductos(){
        return repository.count();
    }

    public List<String> findAllProductosByNombre(){
        return repository.findAllByNombre();
    }

    public Productos findProductoByNombre(String nombre){

        return repository.findByNombre(nombre);

    }
    
}
