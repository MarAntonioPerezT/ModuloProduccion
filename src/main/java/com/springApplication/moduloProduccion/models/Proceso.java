package com.springApplication.moduloProduccion.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proceso{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_proceso")
    private Long codigoProceso;

    @Column(name = "nombre", columnDefinition = "varchar(25)", nullable = false, unique = true)
    private String nombre;

    public Long getCodigo_proceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(Long codigo_proceso) {
        this.codigoProceso = codigo_proceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
