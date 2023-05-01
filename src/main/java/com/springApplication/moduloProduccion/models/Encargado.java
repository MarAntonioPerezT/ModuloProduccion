package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;

@Entity
public class Encargado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEncargado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_usuario", referencedColumnName = "idUsuario")
    private Usuario usuario;

    @Column(columnDefinition = "varchar(15)", nullable = false, unique = true)
    private String tipo;

}
