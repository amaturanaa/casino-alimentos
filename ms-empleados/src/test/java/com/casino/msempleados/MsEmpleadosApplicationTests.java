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

    // @Autowired inyecta el Service REAL
    @Autowired
    private EmpleadoService empleadoService;


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
    @DisplayName("Verificar cantidad de empleados activos")
    void checkEmpleadosActivos() {
        int activos = empleadoService.listarActivos().size();
        log.info("Empleados activos: {}", activos);
        assertEquals(2, activos);
    }

}