package com.casino.msmenu;

import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;
import com.casino.msmenu.service.ProgramacionDiariaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para los métodos de lectura de ProgramacionDiariaService
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class ProgramacionDiariaIntegrationTest {

    @Autowired
    private ProgramacionDiariaService programacionService;

    @Test
    @DisplayName("Verificar programaciones para la fecha 2026-06-10")
    void checkProgramacionesPorFecha() {
        int cantidad = programacionService.listarPorFecha(LocalDate.of(2026, 6, 10)).size();
        log.info("Programaciones del 2026-06-10: {}", cantidad);
        assertEquals(2, cantidad);
    }

    @Test
    @DisplayName("Verificar programaciones de la sede 6")
    void checkProgramacionesPorSede() {
        List<ProgramacionDiariaResponseDTO> lista = programacionService.listarPorSede(6L);
        log.info("Programaciones de la sede 6: {}", lista.size());
        assertEquals(1, lista.size());
        assertEquals("Pollo a la plancha", lista.get(0).getNombrePlato());
    }

    @Test
    @DisplayName("Verificar programaciones por fecha y sede")
    void checkProgramacionPorFechaYSede() {
        int cantidad = programacionService
                .listarPorFechaYSede(LocalDate.of(2026, 6, 10), 6L).size();
        log.info("Programaciones del 2026-06-10 en sede 6: {}", cantidad);
        assertEquals(1, cantidad);
    }
}