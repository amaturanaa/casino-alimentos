package com.casino.msinventario.client;

import com.casino.msinventario.dto.SedeCasinoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-sucursales-inventario", url = "http://localhost:8083")
public interface SucursalesClient {

    @GetMapping("/api/sedes/{id}")
    SedeCasinoResponseDTO obtenerSedePorId(@PathVariable Long id);
}
