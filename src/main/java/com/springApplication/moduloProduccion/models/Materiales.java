package com.springApplication.moduloProduccion.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Materiales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_material")
    private Long codigo_material;
    @Column(name = "nombre", columnDefinition = "varchar(50)", nullable = false, unique = true)
    private String nombre;
    @Column(name = "descripcion", columnDefinition = "varchar(350)", nullable = false)
    private String descripcion;
    @Column(name = "precioCompra", columnDefinition = "decimal(10,2)", nullable = false)
    private Double precioCompra;
    @Column(name = "unidad", columnDefinition = "varchar(10)", nullable = false)
    private String unidad;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "explosion_material",
            joinColumns = @JoinColumn(
                    name = "codigo_material",
                    referencedColumnName = "codigo_material"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "codigo_producto",
                    referencedColumnName = "codigo_producto"
            )
    )
    private List<Productos> productos = new ArrayList<>();

    @OneToMany(mappedBy = "materiales", fetch = FetchType.EAGER)
    private List<Existencias> existencias = new ArrayList<>();

    @ManyToMany(mappedBy = "materiales", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Proveedores> proveedores = new ArrayList<>();
}
