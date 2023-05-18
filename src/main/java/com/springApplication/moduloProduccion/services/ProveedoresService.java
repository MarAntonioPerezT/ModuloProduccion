package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Proveedores;
import com.springApplication.moduloProduccion.models.Proveedores;
import com.springApplication.moduloProduccion.repositories.ProveedoresRepository;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class ProveedoresService implements CrudListener<Proveedores> {

    ProveedoresRepository repository;

    public ProveedoresService(ProveedoresRepository proveedoresRepository){
        this.repository = proveedoresRepository;
    }

    @Override
    public Proveedores add(Proveedores Proveedores) {
        // TODO Auto-generated method stub
        return repository.save(Proveedores);
    }

    @Override
    public void delete(Proveedores Proveedores) {
        // TODO Auto-generated method stub
        repository.delete(Proveedores);
    }

    @Override
    public Proveedores update(Proveedores Proveedores) {
        // TODO Auto-generated method stub
        return repository.save(Proveedores);
    }

    @Override
    public Collection<Proveedores> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    public void saveAllProveedores(List<Proveedores> proveedores){
        repository.saveAll(proveedores);
    }

    public Long count(){
        return repository.count();
    }

    public List<String> obtenerNombresProveedores(){
        return repository.obtenerNombresProveedores();
    }

    public Long obtenerIdProveedorPorRFC(String rfc){
        return repository.obtenerIdProveedorPorRFC(rfc);
    }

    public Proveedores obtenerProveedorPorId(Long idProveedor){
        return repository.findByIdProveedor(idProveedor);
    }

    public List<Proveedores> findAllProveedores(){
        return repository.findAll();
    }

}
