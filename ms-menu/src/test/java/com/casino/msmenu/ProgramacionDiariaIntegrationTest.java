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
    @DisplayName("Verificar programaciones de la sede 6")
    void checkProgramacionesPorSede() {
        List<ProgramacionDiariaResponseDTO> lista = programacionService.listarPorSede(6L);
        log.info("Programaciones de la sede 6: {}", lista.size());
        assertEquals(1, lista.size());
        assertEquals("Pollo a la plancha", lista.get(0).getNombrePlato());
    }

}