package com.casino.mspedidos.repository;

import com.casino.mspedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findBySedeId(Long sedeId);

    List<Pedido> findByEstado(String estado);

    List<Pedido> findByUsuarioIdAndEstado(Long usuarioId, String estado);

}
