package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.sql.Timestamp;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity


public class EstadoProceso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_estadoProceso")
    private Long id_estadoProceso;

    @Column(name = "estado", columnDefinition = "varchar(15)", nullable = false)
    private String nombre;

    @OneToMany (mappedBy = "estadoProceso", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleOrdenes> detalleOrdenes; 

}
