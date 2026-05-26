package com.casino.mspedidos.repository;

import com.casino.mspedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad DetallePedido
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    // Derived Query — busca detalles por el id del pedido asociado
    // El guion bajo (_) navega la relación ManyToOne: pedido.idPedido
    // Equivale a: SELECT * FROM detalle_pedido WHERE pedido_id = ?
    // Usado en mapToDTO para obtener los detalles de un pedido específico
    List<DetallePedido> findByPedido_IdPedido(Long pedidoId);
}