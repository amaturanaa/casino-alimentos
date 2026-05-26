package com.casino.msinventario.repository;

import com.casino.msinventario.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad Ingrediente
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    // Derived Query — filtra ingredientes por sede
    // Equivale a: SELECT * FROM ingrediente WHERE sede_id = ?
    // sedeId es referencia a ms-sucursales sin FK física entre bases de datos
    // Usado para listar todos los ingredientes disponibles en una sede específica
    List<Ingrediente> findBySedeId(Long sedeId);
}