package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.PersonasMoralesProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonasMoralesProveedoresRepository extends JpaRepository<PersonasMoralesProveedor, Long> {

    @Query("SELECT pmp.razonSocial from PersonasMoralesProveedor pmp JOIN PersonaProveedor pp " +
            "ON pp.idPersonaProveedor = pmp.personaProveedor WHERE pp.rfc = ?1")
    String findRazonSocialByRFC(String rfc);
}
