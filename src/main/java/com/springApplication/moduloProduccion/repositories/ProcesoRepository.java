package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Procesos;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProcesoRepository extends JpaRepository<Procesos, Long> {

  @Query("SELECT p FROM Procesos p WHERE lower(p.nombre) LIKE lower(concat('%', :nombre, '%'))")
    Collection<Procesos> search(@Param("nombre") String nombre);

  @Query("SELECT p.nombre FROM Procesos p ORDER BY p.nombre")
    List<String> findAllProcesosByNombre();

  Procesos findByNombre(String nombre);

}
