package com.casino.msmenu.client;

import com.casino.msmenu.dto.CategoriaMenuResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-categorias-menu", url = "http://localhost:8087")
public interface CategoriasMenuClient {

    @GetMapping("/api/categorias/{id}")
    CategoriaMenuResponseDTO obtenerCategoriaPorId(@PathVariable Long id);
}