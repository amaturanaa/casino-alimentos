package com.casino.msinventario.repository;

import com.casino.msinventario.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad MovimientoStock
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {

    // Derived Query — busca movimientos por el id del ingrediente asociado
    // El guion bajo (_) navega la relación ManyToOne: ingrediente.idIngrediente
    // Equivale a: SELECT * FROM movimiento_stock WHERE ingrediente_id = ?
    // Usado para ver el historial completo de movimientos de un ingrediente
    List<MovimientoStock> findByIngrediente_IdIngrediente(Long ingredienteId);

    // Derived Query — busca movimientos por el id del tipo de movimiento
    // El guion bajo (_) navega la relación ManyToOne: tipoMovimiento.idTipoMovimiento
    // Equivale a: SELECT * FROM movimiento_stock WHERE tipo_movimiento_id = ?
    // Usado para filtrar por tipo: solo ENTRADAS, solo SALIDAS o solo MERMAS
    List<MovimientoStock> findByTipoMovimiento_IdTipoMovimiento(Long tipoMovimientoId);
}