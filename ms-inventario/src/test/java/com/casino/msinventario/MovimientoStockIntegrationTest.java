package com.casino.msinventario;

import com.casino.msinventario.dto.MovimientoStockResponseDTO;
import com.casino.msinventario.service.MovimientoStockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para los métodos de lectura de MovimientoStockService
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MovimientoStockIntegrationTest {

    @Autowired
    private MovimientoStockService movimientoService;


    @Test
    @DisplayName("Verificar movimientos del ingrediente 1")
    void checkMovimientosPorIngrediente() {
        List<MovimientoStockResponseDTO> lista = movimientoService.listarPorIngrediente(1L);
        log.info("Movimientos del ingrediente 1: {}", lista.size());
        assertEquals(2, lista.size());
        assertEquals("Arroz", lista.get(0).getNombreIngrediente());
    }

}