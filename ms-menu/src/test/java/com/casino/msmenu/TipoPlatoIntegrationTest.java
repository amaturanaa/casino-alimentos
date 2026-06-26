package com.casino.msmenu;

import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import com.casino.msmenu.service.TipoPlatoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para TipoPlatoService (no usa Feign)
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class TipoPlatoIntegrationTest {

    @Autowired
    private TipoPlatoService tipoPlatoService;

    @Test
    @DisplayName("Verificar nombre del tipo de plato con id 1")
    void checkNombreTipo() {
        TipoPlatoResponseDTO tipo = tipoPlatoService.obtenerPorId(1L);
        log.info("Revisando tipo de plato {}", tipo.getNombreTipoPlato());
        assertEquals("Plato de Fondo", tipo.getNombreTipoPlato());
    }

}