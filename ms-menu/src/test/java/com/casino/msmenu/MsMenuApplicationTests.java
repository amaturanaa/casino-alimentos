package com.casino.msmenu;

import com.casino.msmenu.dto.PlatoResponseDTO;
import com.casino.msmenu.service.PlatoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para PlatoService
// Solo se prueban métodos de lectura — crear() usa Feign hacia ms-categorias-menu
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsMenuApplicationTests {

    @Autowired
    private PlatoService platoService;

    @Test
    void contextLoads() {
    }


    @Test
    @DisplayName("Verificar el tipo de plato del plato 1")
    void checkTipoDelPlato() {
        PlatoResponseDTO plato = platoService.obtenerPorId(1L);
        log.info("Tipo del plato: {}", plato.getNombreTipoPlato());
        assertEquals("Plato de Fondo", plato.getNombreTipoPlato());
    }

    @Test
    @DisplayName("Verificar platos de la categoría 2")
    void checkPlatosPorCategoria() {
        int cantidad = platoService.listarPorCategoria(2L).size();
        log.info("Platos de categoría 2: {}", cantidad);
        assertEquals(1, cantidad);
    }
}