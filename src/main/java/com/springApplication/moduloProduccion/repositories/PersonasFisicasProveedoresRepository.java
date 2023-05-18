package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.PersonasFisicasProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonasFisicasProveedoresRepository extends JpaRepository<PersonasFisicasProveedor, Long> {

    @Query("SELECT CONCAT(pfp.nombre, ' ', pfp.aPaterno) AS nombre FROM PersonasFisicasProveedor pfp " +
            "JOIN pfp.personaProveedor pp " +
            "WHERE pp.rfc = ?1")
    String findNombreProveedorByRFC(String rfc);


}
