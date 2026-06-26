package com.casino.mspagos;

import com.casino.mspagos.dto.TransaccionResponseDTO;
import com.casino.mspagos.service.TransaccionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para TransaccionService
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsPagosApplicationTests {

    @Autowired
    private TransaccionService transaccionService;

    @Test
    void contextLoads() {
    }


    @Test
    @DisplayName("Verificar estado de la transacción del pedido 102")
    void checkTransaccionPorPedido() {
        TransaccionResponseDTO t = transaccionService.obtenerPorPedido(102L);
        log.info("Estado de la transacción del pedido 102: {}", t.getEstadoPago());
        assertEquals("PENDIENTE", t.getEstadoPago());
    }


    @Test
    @DisplayName("Verificar transacciones con estado APROBADO")
    void checkTransaccionesPorEstado() {
        int cantidad = transaccionService.listarPorEstado("APROBADO").size();
        log.info("Transacciones APROBADO: {}", cantidad);
        assertEquals(2, cantidad);
    }

    @Test
    @DisplayName("Verificar transacciones por método EFECTIVO")
    void checkTransaccionesPorMetodo() {
        int cantidad = transaccionService.listarPorMetodo("EFECTIVO").size();
        log.info("Transacciones EFECTIVO: {}", cantidad);
        assertEquals(1, cantidad);
    }
}