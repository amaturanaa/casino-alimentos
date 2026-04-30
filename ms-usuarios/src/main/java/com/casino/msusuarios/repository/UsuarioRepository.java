package com.casino.msusuarios.repository;

import com.casino.msusuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRutUsuario(String rutUsuario);
    List<Usuario> findByActivo(Boolean activo);
    boolean existsByEmail(String email);
    boolean existsByRutUsuario(String rutUsuario);
}