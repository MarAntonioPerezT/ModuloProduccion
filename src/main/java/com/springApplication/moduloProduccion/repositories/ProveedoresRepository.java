package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Clientes;
import com.springApplication.moduloProduccion.models.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ProveedoresRepository extends JpaRepository<Proveedores, Long> {

    @Query("SELECT pmp.razonSocial AS valor FROM PersonasMoralesProveedor pmp UNION SELECT pfp.nombre AS valor FROM PersonasFisicasProveedor pfp")
    List<String> obtenerNombresProveedores();

    @Query("SELECT p.idProveedor FROM Proveedores p " +
            "JOIN PersonaProveedor pp ON pp.idPersonaProveedor = p.personaProveedor " +
            "WHERE pp.rfc = ?1")
    Long obtenerIdProveedorPorRFC(String rfc);
    Proveedores findByIdProveedor(Long idCliente);
}
