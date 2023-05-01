package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;

@Entity
public class OrdenProducto {

    @EmbeddedId
    private OrdenProductoId id;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private String especificaciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("ordenId")
    @JoinColumn(name = "id_orden")
    private Orden orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productoId")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    public OrdenProducto(Orden orden, Producto producto, int cantidad, String especificaciones) {
        this.orden = orden;
        this.producto = producto;
        this.cantidad = cantidad;
        this.especificaciones = especificaciones;
        this.id = new OrdenProductoId(orden.getNp(), producto.getIdProducto());
    }

    public OrdenProducto(){


    }

    public OrdenProductoId getId() {
        return id;
    }

    public void setId(OrdenProductoId id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
