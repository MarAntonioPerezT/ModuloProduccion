package com.springApplication.moduloProduccion.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Procesos{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proceso")
    private Long idProceso;

    @Column(name = "nombre", columnDefinition = "varchar(50)", nullable = false)
    private String nombre;

    @OneToMany (mappedBy = "procesos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleOrdenes> datalleOrdenes = new ArrayList<>();


    
}
