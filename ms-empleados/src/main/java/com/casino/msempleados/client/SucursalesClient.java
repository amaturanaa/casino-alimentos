package com.casino.msempleados.client;

import com.casino.msempleados.dto.SedeCasinoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicación síncrona con ms-sucursales
// @FeignClient define un cliente HTTP declarativo
// name: nombre único del bean en Spring — debe ser distinto entre microservicios
//       que llaman al mismo servicio (ms-sucursales-empleados, ms-sucursales-pedidos, etc.)
// url: dirección del microservicio destino en el entorno local
@FeignClient(name = "ms-sucursales-empleados", url = "http://localhost:8083")
public interface SucursalesClient {

    // Mapea al endpoint GET /api/sedes/{id} de ms-sucursales
    // @PathVariable vincula el parámetro {id} de la URL con el argumento del método
    // Spring deserializa automáticamente la respuesta JSON a SedeCasinoResponseDTO
    // Si ms-sucursales retorna error, Feign lanza FeignException capturada en el Service
    @GetMapping("/api/sedes/{id}")
    SedeCasinoResponseDTO obtenerSedePorId(@PathVariable Long id);
}