package com.casino.msmenu.client;

import com.casino.msmenu.dto.CategoriaMenuResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicación síncrona con ms-categorias-menu
// @FeignClient define un cliente HTTP declarativo
// name: nombre único del bean en Spring para identificar este cliente
// url: dirección del microservicio destino en el entorno local
// ms-categorias-menu corre en el puerto 8087
@FeignClient(name = "ms-categorias-menu", url = "http://localhost:8087")
public interface CategoriasMenuClient {

    // Mapea al endpoint GET /api/categorias/{id} de ms-categorias-menu
    // @PathVariable vincula el parámetro {id} de la URL con el argumento del método
    // Spring deserializa automáticamente la respuesta JSON a CategoriaMenuResponseDTO
    // Si ms-categorias-menu retorna error, Feign lanza FeignException capturada en el Service
    // Usado para verificar que la categoría exista y esté activa antes de crear un plato
    @GetMapping("/api/categorias/{id}")
    CategoriaMenuResponseDTO obtenerCategoriaPorId(@PathVariable Long id);
}