package com.casino.msinventario.repository;

import com.casino.msinventario.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    List<Ingrediente> findBySedeId(Long sedeId);

    List<Ingrediente> findByStockActualLessThanEqual(Double stock);

    List<Ingrediente> findBySedeIdAndStockActualLessThanEqual(Long sedeId, Double stock);
}
