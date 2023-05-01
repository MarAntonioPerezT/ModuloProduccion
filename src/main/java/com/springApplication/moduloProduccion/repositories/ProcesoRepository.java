package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Proceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcesoRepository extends JpaRepository<Proceso, Long> {
    Proceso findByNombre(String nombre);
    @Query("SELECT p.nombre from Proceso p")
    List<String> getAllProcesosByName();
}
