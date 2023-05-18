package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.EstadoProceso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoProcesoRepository extends JpaRepository<EstadoProceso, Long> {

    EstadoProceso findByNombre(String nombre);

}
