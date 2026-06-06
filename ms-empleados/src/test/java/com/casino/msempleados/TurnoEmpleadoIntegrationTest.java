package com.casino.msempleados;

import com.casino.msempleados.dto.TurnoEmpleadoResponseDTO;
import com.casino.msempleados.service.TurnoEmpleadoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para los métodos de lectura de TurnoEmpleadoService
// No se prueba crear() porque ese método usa Feign hacia ms-sucursales
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class TurnoEmpleadoIntegrationTest {

    @Autowired
    private TurnoEmpleadoService turnoService;

    @Test
    @DisplayName("Verificar empleado y tipo del turno con id 1")
    void checkTurnoPorId() {
        TurnoEmpleadoResponseDTO turno = turnoService.obtenerPorId(1L);
        log.info("Revisando turno del empleado {}", turno.getNombreEmpleado());
        assertEquals("Juan Perez", turno.getNombreEmpleado());
        assertEquals("TARDE", turno.getTipoTurno());
    }

    @Test
    @DisplayName("Verificar cantidad total de turnos")
    void checkCantidadTurnos() {
        int cantidad = turnoService.listar().size();
        log.info("Total de turnos: {}", cantidad);
        assertEquals(2, cantidad);
    }

    @Test
    @DisplayName("Verificar cantidad de turnos del empleado 1")
    void checkTurnosPorEmpleado() {
        int turnos = turnoService.listarPorEmpleado(1L).size();
        log.info("Turnos del empleado 1: {}", turnos);
        assertEquals(2, turnos);
    }

    @Test
    @DisplayName("Verificar cantidad de turnos de tipo TARDE")
    void checkTurnosPorTipo() {
        int turnos = turnoService.listarPorTipo("TARDE").size();
        log.info("Turnos tipo TARDE: {}", turnos);
        assertEquals(1, turnos);
    }
}