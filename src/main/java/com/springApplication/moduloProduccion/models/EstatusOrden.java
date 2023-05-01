package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class EstatusOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estatus_orden")
    private Long idEstatusOrden;

    @Column(columnDefinition = "varchar(15)", nullable = false, unique = true)
    private String estado;

    @OneToMany(mappedBy = "estatus", cascade = CascadeType.ALL)
    private List<Orden> ordenes;
}
