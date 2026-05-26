package com.casino.mspedidos.repository;

import com.casino.mspedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad Pedido
// Extiende JpaRepository que provee operaciones CRUD automáticas:
// save(), findById(), findAll(), delete(), existsById(), etc.
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Derived Query — filtra pedidos por usuario
    // Equivale a: SELECT * FROM pedido WHERE usuario_id = ?
    // usuarioId es referencia a ms-usuarios sin FK física
    List<Pedido> findByUsuarioId(Long usuarioId);

    // Derived Query — filtra pedidos por sede
    // Equivale a: SELECT * FROM pedido WHERE sede_id = ?
    // sedeId es referencia a ms-sucursales sin FK física
    List<Pedido> findBySedeId(Long sedeId);

    // Derived Query — filtra pedidos por estado
    // Equivale a: SELECT * FROM pedido WHERE estado = ?
    // Ejemplo: findByEstado("RECIBIDO") o findByEstado("ENTREGADO")
    List<Pedido> findByEstado(String estado);
}