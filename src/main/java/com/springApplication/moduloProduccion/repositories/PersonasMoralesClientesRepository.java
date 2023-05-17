package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.PersonasMoralesCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonasMoralesClientesRepository extends JpaRepository<PersonasMoralesCliente, Long> {

    @Query("SELECT pmc.razonSocial from PersonasMoralesCliente pmc JOIN PersonaCliente pc " +
            "ON pc.idPersonaCliente = pmc.personaCliente WHERE pc.rfc = ?1")
    String findRazonSocialByRFC(String rfc);

}
