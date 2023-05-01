package com.springApplication.moduloProduccion.repositories;

import com.springApplication.moduloProduccion.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioReposiory extends JpaRepository<Usuario, Long> {

    @Query("SELECT u.nombre FROM Usuario u WHERE u.nombre = ?1")
    String findUsuarioNameByNombre(String nombre);
    Usuario findByNombre(String nombre);
}
