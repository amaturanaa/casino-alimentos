package com.casino.mspedidos.client;

import com.casino.mspedidos.dto.SedeCasinoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicación síncrona con ms-sucursales
// Verifica que la sede exista y esté operativa antes de crear un pedido
@FeignClient(name = "ms-sucursales-pedidos", url = "http://localhost:8083")
public interface SucursalesClient {

    @GetMapping("/api/sedes/{id}")
    SedeCasinoResponseDTO obtenerSedePorId(@PathVariable Long id);
}