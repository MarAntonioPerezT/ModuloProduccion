package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.OrdenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenProductoRepository extends JpaRepository <OrdenProducto, Long> {
}
