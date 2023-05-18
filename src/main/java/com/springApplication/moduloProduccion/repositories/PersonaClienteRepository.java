package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.PersonaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonaClienteRepository extends JpaRepository<PersonaCliente, Long> {

    @Query("SELECT pc.rfc from PersonaCliente pc")
    List<String> findAllClientesRFC();
}
