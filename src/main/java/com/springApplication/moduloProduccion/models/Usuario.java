package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(columnDefinition = "varchar(20)", unique = true, nullable = false)
    private String nombre;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Encargado> encargados;

}
