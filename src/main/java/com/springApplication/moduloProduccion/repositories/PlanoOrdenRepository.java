package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.PlanoOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoOrdenRepository extends JpaRepository<PlanoOrden, Long> {

    PlanoOrden findByNombrePlano(String nombrePlano);
}
