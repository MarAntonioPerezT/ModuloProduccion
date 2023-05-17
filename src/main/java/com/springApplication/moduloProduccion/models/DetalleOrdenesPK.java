package com.springApplication.moduloProduccion.models;

import java.io.Serializable;

public class DetalleOrdenesPK implements Serializable {

    private Long orden;
    private Long productos;
    private Long procesos;


    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Long getProductos() {
        return productos;
    }

    public void setProductos(Long productos) {
        this.productos = productos;
    }

    public Long getProcesos() {
        return procesos;
    }

    public void setProcesos(Long procesos) {
        this.procesos = procesos;
    }
}
