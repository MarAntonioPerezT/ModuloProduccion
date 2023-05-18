package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Existencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_existencia")
    private Long id_existencia;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    
    @ManyToOne
    @JoinColumn(name = "codigo_material", nullable = false)
    private Materiales materiales;


    @ManyToOne
    @JoinColumn(name = "codigo_producto", nullable = false)
    private Productos productos;


    @ManyToOne
    @JoinColumn(name = "id_almacen", nullable = false)
    private Almacen almacen;
   
}
