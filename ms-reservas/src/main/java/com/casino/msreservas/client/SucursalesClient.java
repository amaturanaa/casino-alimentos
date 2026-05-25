package com.casino.msreservas.client;

import com.casino.msreservas.dto.SedeCasinoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicación síncrona con ms-sucursales
// @FeignClient define un cliente HTTP declarativo
// name: nombre único del bean en Spring — ms-sucursales-reservas para diferenciarlo
//       de otros microservicios que también llaman a ms-sucursales
// url: dirección del microservicio destino en el entorno local
@FeignClient(name = "ms-sucursales-reservas", url = "http://localhost:8083")
public interface SucursalesClient {

    // Mapea al endpoint GET /api/sedes/{id} de ms-sucursales
    // @PathVariable vincula el parámetro {id} de la URL con el argumento del método
    // Spring deserializa automáticamente la respuesta JSON a SedeCasinoResponseDTO
    // Usado para verificar que la sede exista y esté operativa antes de crear una reserva
    @GetMapping("/api/sedes/{id}")
    SedeCasinoResponseDTO obtenerSedePorId(@PathVariable Long id);
}