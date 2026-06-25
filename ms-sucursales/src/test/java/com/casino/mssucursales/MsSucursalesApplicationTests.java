package com.casino.mssucursales;

import com.casino.mssucursales.dto.SedeCasinoResponseDTO;
import com.casino.mssucursales.service.SedeCasinoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para SedeCasinoService
// @SpringBootTest arranca la app completa y se conecta a la BD de prueba
// @ActiveProfiles("test") fuerza el uso de application-test.properties
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsSucursalesApplicationTests {

    @Autowired
    private SedeCasinoService sedeCasinoService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar nombre de la sede con id 1")
    void checkNombreSede() {
        SedeCasinoResponseDTO sede = sedeCasinoService.obtenerPorId(1L);
        log.info("Revisando sede {}", sede.getNombreSede());
        assertEquals("Casino Central", sede.getNombreSede());
    }

    @Test
    @DisplayName("Verificar que Casino Sur no esta operativa")
    void checkSedeNoOperativa() {
        SedeCasinoResponseDTO sede = sedeCasinoService.obtenerPorId(3L);
        log.info("Estado operativo de {}: {}", sede.getNombreSede(), sede.getEstadoOperativo());
        assertFalse(sede.getEstadoOperativo());
    }

    @Test
    @DisplayName("Verificar cantidad de sedes operativas")
    void checkSedesOperativas() {
        int operativas = sedeCasinoService.listarPorEstado(true).size();
        log.info("Sedes operativas: {}", operativas);
        assertEquals(2, operativas);
    }
}