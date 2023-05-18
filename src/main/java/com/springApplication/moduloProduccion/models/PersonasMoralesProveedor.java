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
public class PersonasMoralesProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersonaMoralProveedor;
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String razonSocial;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonaProveedor personaProveedor;

}
