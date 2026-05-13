package com.casino.msreservas.client;

import com.casino.msreservas.dto.SedeCasinoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-sucursales-reservas", url = "http://localhost:8083")
public interface SucursalesClient {

    @GetMapping("/api/sedes/{id}")
    SedeCasinoResponseDTO obtenerSedePorId(@PathVariable Long id);
}
