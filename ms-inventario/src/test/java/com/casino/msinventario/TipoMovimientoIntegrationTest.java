package com.casino.msinventario;

import com.casino.msinventario.dto.TipoMovimientoResponseDTO;
import com.casino.msinventario.service.TipoMovimientoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para TipoMovimientoService (no usa Feign)
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class TipoMovimientoIntegrationTest {

    @Autowired
    private TipoMovimientoService tipoService;

    @Test
    @DisplayName("Verificar nombre del tipo de movimiento con id 1")
    void checkNombreTipo() {
        TipoMovimientoResponseDTO tipo = tipoService.obtenerPorId(1L);
        log.info("Revisando tipo de movimiento {}", tipo.getNombreTipoMovimiento());
        assertEquals("ENTRADA", tipo.getNombreTipoMovimiento());
    }

    @Test
    @DisplayName("Verificar cantidad total de tipos de movimiento")
    void checkCantidadTipos() {
        int cantidad = tipoService.listar().size();
        log.info("Total de tipos de movimiento: {}", cantidad);
        assertEquals(3, cantidad);
    }
}