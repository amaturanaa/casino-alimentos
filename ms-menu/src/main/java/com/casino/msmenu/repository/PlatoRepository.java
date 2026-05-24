package com.casino.msmenu.repository;

import com.casino.msmenu.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad Plato
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {

    // Derived Query — filtra platos por disponibilidad
    // Equivale a: SELECT * FROM platos WHERE disponible = ?
    // Usado para listar solo platos disponibles o no disponibles
    List<Plato> findByDisponible(Boolean disponible);

    // Derived Query — filtra platos por tipo de plato
    // El guion bajo (_) navega la relación ManyToOne: tipoPlato.idTipoPlato
    // Equivale a: SELECT * FROM platos WHERE tipo_plato_id = ?
    // Ejemplo: listar todos los platos de tipo "Plato de Fondo"
    List<Plato> findByTipoPlato_IdTipoPlato(Long idTipoPlato);

    // Derived Query — filtra platos por categoría de menú
    // Equivale a: SELECT * FROM platos WHERE categoria_id = ?
    // categoriaId es referencia a ms-categorias-menu sin FK física
    List<Plato> findByCategoriaId(Long categoriaId);
}