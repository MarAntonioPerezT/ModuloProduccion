package com.springApplication.moduloProduccion.services;

import com.springApplication.moduloProduccion.models.Orden;
import com.springApplication.moduloProduccion.models.OrdenProducto;
import com.springApplication.moduloProduccion.models.Producto;
import com.springApplication.moduloProduccion.repositories.OrdenProductoRepository;
import com.springApplication.moduloProduccion.repositories.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenProductoService {

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    public OrdenProductoService(OrdenProductoRepository ordenProductoRepository, OrdenRepository ordenRepository){
        this.ordenProductoRepository = ordenProductoRepository;
        this.ordenRepository = ordenRepository;
    }

    public void saveOrdenConProductos(Orden orden, List<Producto> productos, List<Integer> cantidades, List<String> especificaciones){

        orden.agregarProductosAOrden(productos, cantidades, especificaciones);
        ordenRepository.save(orden);
    }

    public List<OrdenProducto> findAllOrdenesProductos(){
        return ordenProductoRepository.findAll();
    }

}
