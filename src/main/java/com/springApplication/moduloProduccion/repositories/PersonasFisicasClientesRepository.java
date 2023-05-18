package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.PersonaCliente;
import com.springApplication.moduloProduccion.models.PersonasFisicasCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonasFisicasClientesRepository extends JpaRepository<PersonasFisicasCliente, Long> {

    @Query("SELECT pfc.personaCliente FROM PersonasFisicasCliente pfc")
    List<PersonaCliente> findAllPersonasCliente();

    @Query("SELECT CONCAT(pfc.nombre, ' ', pfc.aPaterno) AS nombre FROM PersonasFisicasCliente pfc " +
            "JOIN pfc.personaCliente pc " +
            "WHERE pc.rfc = ?1")
    String findNombreClienteByRFC(String rfc);
}
