package com.casino.msinventario.repository;

import com.casino.msinventario.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Long> {

    Optional<TipoMovimiento> findByNombreTipoMovimiento(String nombre);

    boolean existsByNombreTipoMovimiento(String nombre);
}
