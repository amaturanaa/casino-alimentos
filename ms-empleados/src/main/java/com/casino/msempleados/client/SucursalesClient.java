package com.casino.msempleados.client;

import com.casino.msempleados.dto.SedeCasinoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-sucursales-empleados", url = "http://localhost:8083")
public interface SucursalesClient {

    @GetMapping("/api/sedes/{id}")
    SedeCasinoResponseDTO obtennerSedePorId(@PathVariable Long id);
}
