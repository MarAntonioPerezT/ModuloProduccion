package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity


public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "np")
    private Long np;

    @Column(name = "fechaEstimadInicio", nullable = false)
    private Date fechaEstimadaInicio;

    @Column(name = "fechaEstimadaFinal", nullable = false)
    private Date fechaEstimadaFinal;

    @Column(name = "fechaRealIniciol", nullable = false)
    private Date fechaRealIniciol;

    @Column(name = "fechaRealFinal", nullable = false)
    private Date fechaRealFinal;

    @Column(name = "motivo", columnDefinition = "varchar(100)", nullable = true)
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes clientes;

    @ManyToOne
    @JoinColumn(name = "id_estadoOrden", nullable = false)
    private EstadoOrden estadoOrden;

    @OneToMany (mappedBy = "orden", fetch = FetchType.EAGER)
    private List<DetalleOrdenes> datalleOrdenes = new ArrayList<>();

  
}
