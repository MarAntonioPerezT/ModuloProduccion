package com.springApplication.moduloProduccion.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springApplication.moduloProduccion.models.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Long>{
 
    @Query("SELECT p FROM Productos p WHERE lower(p.nombre) LIKE lower(concat('%', :nombre, '%'))")
    Collection<Productos> search(@Param("nombre") String nombre);

    @Query("SELECT p.nombre FROM Productos p")
    List<String> findAllByNombre();
    Productos findByNombre(String nombre);
}
