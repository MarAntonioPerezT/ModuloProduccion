package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.DetalleOrdenes;
import com.springApplication.moduloProduccion.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrdenes, Long> {

    @Query(nativeQuery = true, value = "SELECT p.nombre from productos p " +
            "JOIN detalle_ordenes do ON do.codigo_producto = p.codigo_producto " +
            "WHERE do.np =?1 " +
            "GROUP BY p.nombre")
    List<String> findAllProductosInOrdenByOrden(Long idOrden);
}
