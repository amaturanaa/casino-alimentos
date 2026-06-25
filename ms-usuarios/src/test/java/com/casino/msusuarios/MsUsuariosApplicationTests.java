package com.casino.msusuarios;

import com.casino.msusuarios.dto.UsuarioResponseDTO;
import com.casino.msusuarios.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para UsuarioService
// @SpringBootTest arranca la app completa y se conecta a la BD de prueba
// @ActiveProfiles("test") fuerza el uso de application-test.properties
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsUsuariosApplicationTests {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar rol del usuario administrador")
    void checkRolAdmin() {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorEmail("admin@casino.cl");
        log.info("Revisando rol de {}", usuario.getEmail());
        assertEquals("ROLE_ADMIN", usuario.getNombre_rol());
    }

    @Test
    @DisplayName("Verificar baja logica — usuario inactivo")
    void checkUsuarioInactivo() {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorEmail("cliente@casino.cl");
        log.info("Estado activo de {}: {}", usuario.getEmail(), usuario.getActivo());
        assertFalse(usuario.getActivo());
    }

    @Test
    @DisplayName("Verificar cantidad de usuarios activos")
    void checkUsuariosActivos() {
        int activos = usuarioService.listarUsuariosActivos().size();
        log.info("Usuarios activos: {}", activos);
        assertEquals(2, activos);
    }
}