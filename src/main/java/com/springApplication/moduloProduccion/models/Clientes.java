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
public class Clientes {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonaCliente personaCliente;

    @OneToMany (mappedBy = "clientes", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Orden> orden; 
   
}
