package com.casino.mscategoriasmenu.repository;


import com.casino.mscategoriasmenu.model.CategoriaMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaMenuRepository extends JpaRepository<CategoriaMenu, Long> {

    List<CategoriaMenu> findByEstado(Boolean estado);

    boolean existsBynombre(String nombre);
}
