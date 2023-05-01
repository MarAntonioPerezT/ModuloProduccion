package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Producto;
import com.springApplication.moduloProduccion.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }
    public List<Producto> getAllProductos(){
        return productoRepository.findAll();
    }

    public Long countProductos(){
        return productoRepository.count();
    }

    public List<String> getAllProductosByNombre(){
        return productoRepository.findAllNombre();
    }

    public Producto findProductoByNombre(String nombre){

        return productoRepository.findByNombre(nombre);

    }
}
