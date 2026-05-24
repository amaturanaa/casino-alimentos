package com.casino.mscategoriasmenu.repository;

import com.casino.mscategoriasmenu.model.EtiquetaNutricional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repositorio JPA para acceso a datos de la entidad EtiquetaNutricional
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
public interface EtiquetaNutricionalRepository extends JpaRepository<EtiquetaNutricional, Long> {

    // Derived Query — busca una etiqueta por el id de su categoría asociada
    // El guion bajo (_) navega la relación OneToOne: categoriaMenu.id
    // Equivale a: SELECT * FROM etiqueta_nutricional WHERE categoria_id = ?
    // Retorna Optional para manejar el caso de etiqueta no encontrada
    // Usado para obtener la etiqueta nutricional de una categoría específica
    Optional<EtiquetaNutricional> findByCategoriaMenu_Id(Long categoriaId);

    // Derived Query — verifica si existe una etiqueta para esa categoría
    // El guion bajo (_) navega la relación OneToOne: categoriaMenu.id
    // Equivale a: SELECT COUNT(*) > 0 FROM etiqueta_nutricional WHERE categoria_id = ?
    // Usado en el Service para validar que no exista ya una etiqueta antes de crear
    boolean existsByCategoriaMenu_Id(Long categoriaId);
}