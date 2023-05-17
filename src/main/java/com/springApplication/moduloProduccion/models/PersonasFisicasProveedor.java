package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class PersonasFisicasProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonaFisicaProveedor;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String aPaterno;
    @Column(columnDefinition = "varchar(50)")
    private String aMaterno;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String nombre;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonaProveedor personaProveedor;


}
