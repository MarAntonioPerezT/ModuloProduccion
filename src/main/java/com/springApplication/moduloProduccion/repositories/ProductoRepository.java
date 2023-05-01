package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p.nombre FROM Producto p")
    List<String> findAllNombre();

    Producto findByNombre(String nombre);
}
