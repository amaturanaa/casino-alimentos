package com.casino.msproveedores.repository;

import com.casino.msproveedores.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

// Repositorio JPA para acceso a datos de la entidad Proveedor
// Extiende JpaRepository que provee operaciones CRUD automáticas
// Spring Data JPA genera la implementación automáticamente en tiempo de ejecución
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    // Derived Query — busca un proveedor por su RUT
    // Equivale a: SELECT * FROM proveedor WHERE rut_proveedor = ?
    // Retorna Optional para manejar el caso de proveedor no encontrado
    Optional<Proveedor> findByRutProveedor(String rutProveedor);

    // Derived Query — verifica si existe un proveedor con ese RUT
    // Equivale a: SELECT COUNT(*) > 0 FROM proveedor WHERE rut_proveedor = ?
    // Usado en el Service para validar RUT único antes de crear un proveedor
    boolean existsByRutProveedor(String rutProveedor);

    // Derived Query — filtra proveedores por estado activo
    // Equivale a: SELECT * FROM proveedor WHERE activo = ?
    // Usado para listar solo proveedores activos o inactivos
    List<Proveedor> findByActivo(Boolean activo);
}