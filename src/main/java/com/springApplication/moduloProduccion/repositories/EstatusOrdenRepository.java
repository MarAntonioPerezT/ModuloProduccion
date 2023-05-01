package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.EstatusOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstatusOrdenRepository extends JpaRepository<EstatusOrden, Long> {

    @Query("SELECT e.estado FROM EstatusOrden e WHERE e.estado = ?1")
    String findEstatusNombreByNombre(String nombre);

    EstatusOrden findByEstado(String estado);
}
