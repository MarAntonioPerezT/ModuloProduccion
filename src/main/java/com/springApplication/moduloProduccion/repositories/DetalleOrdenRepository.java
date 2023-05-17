package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.DetalleOrdenes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrdenes, Long> {
}