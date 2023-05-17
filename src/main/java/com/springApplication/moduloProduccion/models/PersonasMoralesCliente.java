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
public class PersonasMoralesCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonaMoralCliente;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String razonSocial;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonaCliente personaCliente;
}
