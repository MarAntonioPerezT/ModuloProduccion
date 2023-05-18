package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
}
