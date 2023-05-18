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

public class PlanoOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlano;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String nombrePlano;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] pdfContent;

    @OneToMany(mappedBy = "planoOrden", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DetalleOrdenes> detalleOrdenes = new ArrayList<>();

}