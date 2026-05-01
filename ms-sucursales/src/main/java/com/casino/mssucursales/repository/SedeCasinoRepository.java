package com.casino.mssucursales.repository;


import com.casino.mssucursales.model.SedeCasino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SedeCasinoRepository extends JpaRepository<SedeCasino, Long> {

    List<SedeCasino> findByEstadoOperativo(Boolean estado);
}
