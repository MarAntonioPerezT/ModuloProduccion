package com.springApplication.moduloProduccion.models;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Proveedores{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonaProveedor personaProveedor;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "proveedor_material",
            joinColumns = @JoinColumn(
                name = "id_proveedor",
                referencedColumnName = "idProveedor"
    ),
            inverseJoinColumns = @JoinColumn(
                    name = "codigo_material",
                    referencedColumnName = "codigo_material"
            )
    )
    private List<Materiales> materiales = new ArrayList<>();
    
}
