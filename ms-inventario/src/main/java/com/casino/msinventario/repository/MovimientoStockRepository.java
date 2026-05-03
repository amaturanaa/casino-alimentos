package com.casino.msinventario.repository;

import com.casino.msinventario.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {

    List<MovimientoStock> findByIngrediente_IdIngrediente(Long ingredienteId);

    List<MovimientoStock> findByTipoMovimiento_IdTipoMovimiento(Long tipoMovimientoId);
}