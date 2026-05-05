package com.casino.msproveedores.repository;

import com.casino.msproveedores.model.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    List<OrdenCompra> findByProveedor_IdProveedor(Long proveedorId);
    List<OrdenCompra> findBySedeId(Long sedeId);
    List<OrdenCompra> findByEstado(String estado);
}

