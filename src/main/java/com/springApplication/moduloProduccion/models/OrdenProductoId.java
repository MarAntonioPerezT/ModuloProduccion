package com.springApplication.moduloProduccion.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrdenProductoId {

    @Column(name = "id_orden")
    private Long ordenId;
    @Column(name = "id_producto")
    private Long productoId;

    public OrdenProductoId(Long ordenId, Long productoId) {
        this.ordenId = ordenId;
        this.productoId = productoId;
    }

    public OrdenProductoId(){


    }

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
}
