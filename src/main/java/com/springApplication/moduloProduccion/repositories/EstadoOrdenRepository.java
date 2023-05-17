package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, Long> {

    @Query("SELECT e.estado FROM EstadoOrden e WHERE e.estado = ?1")
    String findEstatusNombreByNombre(String nombre);

    EstadoOrden findByEstado(String estado);
}
