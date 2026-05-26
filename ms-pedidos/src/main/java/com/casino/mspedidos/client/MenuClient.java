package com.casino.mspedidos.client;

import com.casino.mspedidos.dto.PlatoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicación síncrona con ms-menu
// Verifica que el plato exista y esté disponible antes de crear el pedido
@FeignClient(name = "ms-menu-pedidos", url = "http://localhost:8084")
public interface MenuClient {

    @GetMapping("/api/platos/{id}")
    PlatoResponseDTO obtenerPlatoPorId(@PathVariable Long id);
}