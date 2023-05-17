package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    @Query("SELECT pmc.razonSocial AS nombreClientes FROM PersonasMoralesCliente pmc "
            + "UNION ALL "
            + "SELECT pfc.nombre AS nombreClientes FROM PersonasFisicasCliente pfc")
    List<String> obtenerNombresClientes();
    @Query ("SELECT c.personaCliente from Clientes c WHERE c.personaCliente = ?1")
    Long obtenerClientePorIdPersona(Long idPersonaCliente);

    @Query("SELECT c.idCliente FROM Clientes c " +
            "JOIN PersonaCliente pc ON pc.idPersonaCliente = c.personaCliente " +
            "WHERE pc.rfc = ?1")
    Long obtenerIdClientePorRFC(String rfc);

    Clientes findByIdCliente(Long idCliente);

}
