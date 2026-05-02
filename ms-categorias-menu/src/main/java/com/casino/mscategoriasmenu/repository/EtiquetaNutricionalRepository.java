package com.casino.mscategoriasmenu.repository;


import com.casino.mscategoriasmenu.model.EtiquetaNutricional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiquetaNutricionalRepository extends JpaRepository<EtiquetaNutricional, Long> {

    Optional<EtiquetaNutricional> findByCategoriaMenuId(Long categoriaId);

    boolean existsByCategoriaMenuId(Long categoriaId);
}
