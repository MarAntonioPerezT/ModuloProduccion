package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class DetalleOrdenes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long id_detalle;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "especificaciones", columnDefinition = "varchar(100)", nullable = false)
    private String especificaciones;

    @Column(name = "nombreEncargado", columnDefinition = "varchar(100)", nullable = false)
    private String nombreEncargado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "np", referencedColumnName = "np", nullable = false)
    private Orden orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto", nullable = false)
    private Productos productos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proceso", referencedColumnName = "id_proceso", nullable = false)
    private Procesos procesos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estadoProceso", referencedColumnName = "id_estadoProceso", nullable = false)
    private EstadoProceso estadoProceso;

}


