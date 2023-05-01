package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Orden {

    @Id
    private Long np;
    @Column(columnDefinition = "date", nullable = false)
    private Date fechaInicio;
    @Column(columnDefinition = "date", nullable = false)
    private Date fechaFinal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estatus", referencedColumnName = "id_estatus_orden")
    private EstatusOrden estatus;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrdenProducto> ordenProductos = new HashSet<>();

    public Set<OrdenProducto> getOrdenProductos() {
        return ordenProductos;
    }

    public void setOrdenProductos(Set<OrdenProducto> ordenProductos) {
        this.ordenProductos = ordenProductos;
    }

    public Long getNp() {
        return np;
    }

    public void setNp(Long np) {
        this.np = np;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstatusOrden getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusOrden estatus) {
        this.estatus = estatus;
    }

    public void agregarProductosAOrden(List<Producto> productos, List<Integer> cantidades, List<String> descripciones){

        if (productos.size() == cantidades.size() && productos.size() == descripciones.size()){

            for (int i = 0; i < productos.size(); i++){

                agregarProducto(productos.get(i), cantidades.get(i), descripciones.get(i));

            }
        }
    }

    public void agregarProducto(Producto producto, int cantidad, String descripcion){

        OrdenProducto ordenProducto = new OrdenProducto(this, producto, cantidad, descripcion);
        ordenProductos.add(ordenProducto);
        producto.getOrdenProductos().add(ordenProducto);

    }
}
