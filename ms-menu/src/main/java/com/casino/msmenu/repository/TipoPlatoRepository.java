package com.casino.msmenu.repository;

import com.casino.msmenu.model.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Repositorio JPA para acceso a datos de la entidad TipoPlato
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface TipoPlatoRepository extends JpaRepository<TipoPlato, Long> {

    // Derived Query — busca un tipo de plato por su nombre
    // Equivale a: SELECT * FROM tipo_plato WHERE nombre_tipo_plato = ?
    // Retorna Optional para manejar el caso de tipo no encontrado
    // Usado para buscar tipos por nombre sin necesidad del id
    Optional<TipoPlato> findByNombreTipoPlato(String nombre);

    // Derived Query — verifica si existe un tipo de plato con ese nombre
    // Equivale a: SELECT COUNT(*) > 0 FROM tipo_plato WHERE nombre_tipo_plato = ?
    // Usado en el Service para validar nombre único antes de crear un tipo de plato
    // Evita duplicar tipos como "Plato de Fondo", "Entrada", "Postre"
    boolean existsByNombreTipoPlato(String nombre);
}