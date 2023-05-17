package com.springApplication.moduloProduccion.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springApplication.moduloProduccion.models.Materiales;

public interface MaterialesRespository extends JpaRepository<Materiales, Long>{
    
    @Query("SELECT p FROM Materiales p WHERE lower(p.nombre) LIKE lower(concat('%', :nombre, '%'))")
    Collection<Materiales> search(@Param("nombre") String nombre);
}
