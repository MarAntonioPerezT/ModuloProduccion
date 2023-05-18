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

public class EstadoOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_estadoOrden")
    private Long id_estadoOrden;

    @Column(name = "estado", columnDefinition = "varchar(15)", nullable = false)
    private String estado;

    @OneToMany (mappedBy = "estadoOrden", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Orden> orden; 
    

}
