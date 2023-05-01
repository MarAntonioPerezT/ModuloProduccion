package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c.nombre FROM Cliente c")
    List<String> findAllClientesByNombre();
    Cliente findByNombre(String nombre);

}
