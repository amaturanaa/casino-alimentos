package com.casino.mspagos.repository;

import com.casino.mspagos.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    Optional<Transaccion> findByPedidoId(Long pedidoId);

    List<Transaccion> findByUsuarioId(Long usuarioId);

    List<Transaccion> findByEstadoPago(String estadoPago);

    List<Transaccion> findByMetodoPago(String metodoPago);
}
