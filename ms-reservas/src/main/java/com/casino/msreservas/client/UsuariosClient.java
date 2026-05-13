package com.casino.msreservas.client;

import com.casino.msreservas.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8082")
public interface UsuariosClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioResponseDTO obtenerUsuarioPorId(@PathVariable Long id);
}
