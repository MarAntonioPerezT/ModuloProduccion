package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Almacen {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    @Column (name ="id_almacen")
    private Long id_almacen; 

    @Column(name = "nombre_almacen", columnDefinition = "varchar(50)", nullable = false)
    private String nombre_almacen;

    @OneToMany (mappedBy = "almacen") 
    private List<Existencias> existencias;

    
}
