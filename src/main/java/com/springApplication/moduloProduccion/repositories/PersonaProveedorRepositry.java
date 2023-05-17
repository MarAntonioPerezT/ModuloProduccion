package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.PersonaProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonaProveedorRepositry extends JpaRepository<PersonaProveedor, Long> {

    @Query("SELECT pp.rfc FROM PersonaProveedor pp")
    List<String> findAllProveedoresByRFC();

    PersonaProveedor findByRfc(String rfc);
}
