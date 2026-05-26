package com.casino.msproveedores.repository;

import com.casino.msproveedores.model.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repositorio JPA para acceso a datos de la entidad OrdenCompra
// Extiende JpaRepository que provee operaciones CRUD automáticas
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    // Derived Query — filtra órdenes por proveedor
    // El guion bajo (_) navega la relación ManyToOne: proveedor.idProveedor
    // Equivale a: SELECT * FROM orden_compra WHERE proveedor_id = ?
    List<OrdenCompra> findByProveedor_IdProveedor(Long proveedorId);

    // Derived Query — filtra órdenes por sede
    // Equivale a: SELECT * FROM orden_compra WHERE sede_id = ?
    // sedeId es referencia a ms-sucursales sin FK física
    List<OrdenCompra> findBySedeId(Long sedeId);

    // Derived Query — filtra órdenes por estado
    // Equivale a: SELECT * FROM orden_compra WHERE estado = ?
    // Ejemplo: findByEstado("PENDIENTE") o findByEstado("RECIBIDA")
    List<OrdenCompra> findByEstado(String estado);
}