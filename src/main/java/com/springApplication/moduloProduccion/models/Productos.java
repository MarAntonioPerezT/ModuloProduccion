package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_producto")
    private Long codigo_producto;
    @Column(name = "nombre", columnDefinition = "varchar(25)", nullable = false)
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "varchar(250)", nullable = false)
    private String descripcion;
    @Column(name = "precioVenta", columnDefinition = "decimal(10,2)", nullable = false)
    private Double precioVenta;

    @ManyToMany(mappedBy = "productos")
    private List<Materiales> materiales = new ArrayList<>();

    @OneToMany (mappedBy = "productos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleOrdenes> datalleOrdenes = new ArrayList<>();

    @OneToMany(mappedBy = "productos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Existencias> existencias = new ArrayList<>();


}
