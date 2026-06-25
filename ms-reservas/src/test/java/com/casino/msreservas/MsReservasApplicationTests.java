package com.casino.msreservas;

import com.casino.msreservas.dto.ReservaResponseDTO;
import com.casino.msreservas.dto.TurnoDisponibleResponseDTO;
import com.casino.msreservas.service.ReservaService;
import com.casino.msreservas.service.TurnoDisponibleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para ReservaService y TurnoDisponibleService
// Solo se prueban métodos de lectura — ReservaService.crear() usa Feign hacia ms-usuarios y ms-sucursales
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsReservasApplicationTests {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private TurnoDisponibleService turnoService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar estado de la reserva con id 1")
    void checkEstadoReserva() {
        ReservaResponseDTO reserva = reservaService.obtenerPorId(1L);
        log.info("Estado de la reserva 1: {}", reserva.getEstado());
        assertEquals("ACTIVA", reserva.getEstado());
    }

    @Test
    @DisplayName("Verificar reservas activas (filtro por estado)")
    void checkReservasPorEstado() {
        List<ReservaResponseDTO> activas = reservaService.listarPorEstado("ACTIVA");
        log.info("Reservas ACTIVA: {}", activas.size());
        assertEquals(2, activas.size());
    }

    @Test
    @DisplayName("Verificar turnos disponibles con cupos en fecha 2026-06-23")
    void checkTurnosDisponibles() {
        List<TurnoDisponibleResponseDTO> disponibles = turnoService
                .listarDisponiblesPorSedeYFecha(6L, LocalDate.of(2026, 6, 23));
        log.info("Turnos disponibles sede 6 el 2026-06-23: {}", disponibles.size());
        assertEquals(1, disponibles.size());
    }
}