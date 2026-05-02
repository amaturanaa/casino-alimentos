package com.casino.msmenu.repository;

import com.casino.msmenu.model.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoPlatoRepository extends JpaRepository<TipoPlato, Long> {

    Optional<TipoPlato> findByNombreTipoPlato(String nombre);

    boolean existsByNombreTipoPlato(String nombre);
}
