package com.casino.mspagos.client;

import com.casino.mspagos.dto.PedidoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 'name' identifica al servicio en el ecosistema Eureka.
// 'url' asegura que apunte directo al puerto local del ms-pedidos (8085).
@FeignClient(name = "ms-pedidos", url = "http://localhost:8085")
public interface PedidosClient {

    @GetMapping("/api/pedidos/{id}")
    PedidoResponseDTO obtenerPedidoPorId(@PathVariable("id") Long id);
}