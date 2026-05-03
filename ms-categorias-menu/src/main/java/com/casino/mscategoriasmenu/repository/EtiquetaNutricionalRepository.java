package com.casino.mscategoriasmenu.repository;


import com.casino.mscategoriasmenu.model.EtiquetaNutricional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiquetaNutricionalRepository extends JpaRepository<EtiquetaNutricional, Long> {

    Optional<EtiquetaNutricional> findByCategoriaMenu_Id(Long categoriaId);

    boolean existsByCategoriaMenu_Id(Long categoriaId);
}
