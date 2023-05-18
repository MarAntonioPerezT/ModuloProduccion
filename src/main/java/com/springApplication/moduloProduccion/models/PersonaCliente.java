package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
public class PersonaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonaCliente;
    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String calle;
    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String codigoPostal;
    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String colonia;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String email;
    @Column(columnDefinition = "varchar(50)" , nullable = false)
    private String estado;
    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String municipio;
    @Column(columnDefinition = "int", nullable = false)
    private Integer numero;
    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String rfc;
    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "personaCliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PersonasMoralesCliente> personasMoralesClientes = new ArrayList<>();

    @OneToMany(mappedBy = "personaCliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PersonasFisicasCliente> personasFisicasClientes = new ArrayList<>();

    @OneToMany(mappedBy = "personaCliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Clientes> clientes = new ArrayList<>();


}
