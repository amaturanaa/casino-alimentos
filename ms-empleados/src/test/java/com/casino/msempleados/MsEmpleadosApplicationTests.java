package com.casino.msempleados;

import com.casino.msempleados.dto.EmpleadoResponseDTO;
import com.casino.msempleados.service.EmpleadoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para EmpleadoService
// @SpringBootTest arranca la app completa y se conecta a la BD de prueba
// @ActiveProfiles("test") fuerza el uso de application-test.properties
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsEmpleadosApplicationTests {

    // @Autowired inyecta el Service REAL desde el contexto de Spring
    @Autowired
    private EmpleadoService empleadoService;

    // Verifica que el contexto de Spring carga correctamente
    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar nombre completo del empleado con id 1")
    void checkNombreEmpleado() {
        EmpleadoResponseDTO empleado = empleadoService.obtenerPorId(1L);
        log.info("Revisando empleado {}", empleado.getNombreCompleto());
        assertEquals("Juan Carlos Perez Soto", empleado.getNombreCompleto());
    }

    @Test
    @DisplayName("Verificar cargo del empleado por su RUT")
    void checkEmpleadoPorRut() {
        EmpleadoResponseDTO empleado = empleadoService.obtenerPorRut("11111111");
        log.info("Revisando RUT del empleado {}", empleado.getNombreCompleto());
        assertEquals("Cocinero", empleado.getCargo());
    }

    @Test
    @DisplayName("Verificar cantidad total de empleados")
    void checkCantidadEmpleados() {
        int cantidad = empleadoService.listar().size();
        log.info("Total de empleados: {}", cantidad);
        assertEquals(3, cantidad);
    }

    @Test
    @DisplayName("Verificar cantidad de empleados activos")
    void checkEmpleadosActivos() {
        int activos = empleadoService.listarActivos().size();
        log.info("Empleados activos: {}", activos);
        assertEquals(2, activos);
    }

    @Test
    @DisplayName("Verificar cantidad de empleados con cargo Cocinero")
    void checkEmpleadosPorCargo() {
        int cocineros = empleadoService.listarPorCargo("Cocinero").size();
        log.info("Cocineros: {}", cocineros);
        assertEquals(1, cocineros);
    }
}