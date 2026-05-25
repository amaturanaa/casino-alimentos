package com.casino.msreservas.client;

import com.casino.msreservas.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicación síncrona con ms-usuarios
// @FeignClient define un cliente HTTP declarativo
// name: nombre único del bean en Spring para identificar este cliente
// url: dirección del microservicio destino en el entorno local
@FeignClient(name = "ms-usuarios", url = "http://localhost:8082")
public interface UsuariosClient {

    // Mapea al endpoint GET /api/usuarios/{id} de ms-usuarios
    // @PathVariable vincula el parámetro {id} de la URL con el argumento del método
    // Spring deserializa automáticamente la respuesta JSON a UsuarioResponseDTO
    // Usado para verificar que el usuario exista y esté activo antes de crear una reserva
    @GetMapping("/api/usuarios/{id}")
    UsuarioResponseDTO obtenerUsuarioPorId(@PathVariable Long id);
}