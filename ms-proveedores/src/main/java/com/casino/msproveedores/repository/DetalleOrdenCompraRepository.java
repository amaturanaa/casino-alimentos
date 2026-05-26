package com.casino.msproveedores.repository;

import com.casino.msproveedores.model.DetalleOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad DetalleOrdenCompra
// Extiende JpaRepository que provee operaciones CRUD automáticas
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface DetalleOrdenCompraRepository extends JpaRepository<DetalleOrdenCompra, Long> {

    // Derived Query — busca detalles por el id de la orden de compra asociada
    // El guion bajo (_) navega la relación ManyToOne: ordenCompra.idOrdenCompra
    // Equivale a: SELECT * FROM detalle_orden_compra WHERE orden_compra_id = ?
    // Usado en mapToDTO para obtener los detalles de una orden específica
    List<DetalleOrdenCompra> findByOrdenCompra_IdOrdenCompra(Long ordenCompraId);
}