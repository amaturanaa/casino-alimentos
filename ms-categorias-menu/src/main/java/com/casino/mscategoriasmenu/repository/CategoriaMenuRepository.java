package com.casino.mscategoriasmenu.repository;

import com.casino.mscategoriasmenu.model.CategoriaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad CategoriaMenu
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
public interface CategoriaMenuRepository extends JpaRepository<CategoriaMenu, Long> {

    // Derived Query — filtra categorías por estado
    // Equivale a: SELECT * FROM categoria_menu WHERE estado = ?
    // Usado para listar solo categorías activas o inactivas
    // Ejemplo: findByEstado(true) retorna todas las categorías activas
    List<CategoriaMenu> findByEstado(Boolean estado);

    // Derived Query — verifica si existe una categoría con ese nombre
    // Equivale a: SELECT COUNT(*) > 0 FROM categoria_menu WHERE nombre = ?
    // Usado en el Service para validar nombre único antes de crear una categoría
    boolean existsByNombre(String nombre);
}