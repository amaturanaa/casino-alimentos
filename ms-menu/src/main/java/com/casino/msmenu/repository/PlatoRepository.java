package com.casino.msmenu.repository;

import com.casino.msmenu.model.Plato;
import com.casino.msmenu.model.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {

    List<Plato> findByDisponible(Boolean disponible);

    List<Plato> findByTipoPlato_IdTipoPlato(Long idTipoPlato);

    List<Plato> findByCategoriaId(Long categoriaId);
}
