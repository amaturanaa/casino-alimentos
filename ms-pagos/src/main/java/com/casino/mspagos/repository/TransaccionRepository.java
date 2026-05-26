package com.casino.mspagos.repository;

import com.casino.mspagos.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Repositorio JPA para acceso a datos de la entidad Transaccion
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    // Derived Query — busca una transacción por el id del pedido
    // Equivale a: SELECT * FROM transaccion WHERE pedido_id = ?
    // Retorna Optional — un pedido solo puede tener una transacción (1 a 1)
    // Usado para verificar que no exista ya un pago antes de procesar uno nuevo
    Optional<Transaccion> findByPedidoId(Long pedidoId);

    // Derived Query — filtra transacciones por usuario
    // Equivale a: SELECT * FROM transaccion WHERE usuario_id = ?
    // usuarioId es referencia a ms-usuarios sin FK física
    List<Transaccion> findByUsuarioId(Long usuarioId);

    // Derived Query — filtra transacciones por estado de pago
    // Equivale a: SELECT * FROM transaccion WHERE estado_pago = ?
    // Ejemplo: findByEstadoPago("APROBADO")
    List<Transaccion> findByEstadoPago(String estadoPago);

    // Derived Query — filtra transacciones por método de pago
    // Equivale a: SELECT * FROM transaccion WHERE metodo_pago = ?
    // Ejemplo: findByMetodoPago("JUNAEB")
    List<Transaccion> findByMetodoPago(String metodoPago);
}