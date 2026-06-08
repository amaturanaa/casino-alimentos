package com.casino.msinventario;

import com.casino.msinventario.dto.IngredienteResponseDTO;
import com.casino.msinventario.service.IngredienteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para IngredienteService
// Solo se prueban métodos de lectura — crear() usa Feign hacia ms-sucursales
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsInventarioApplicationTests {

    @Autowired
    private IngredienteService ingredienteService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar nombre del ingrediente con id 1")
    void checkNombreIngrediente() {
        IngredienteResponseDTO ing = ingredienteService.obtenerPorId(1L);
        log.info("Revisando ingrediente {}", ing.getNombreIngrediente());
        assertEquals("Arroz", ing.getNombreIngrediente());
    }

    @Test
    @DisplayName("Verificar que el Arroz NO tiene stock bajo")
    void checkArrozStockOk() {
        IngredienteResponseDTO ing = ingredienteService.obtenerPorId(1L);
        log.info("Stock bajo del Arroz: {}", ing.getStockBajo());
        assertFalse(ing.getStockBajo());
    }

    @Test
    @DisplayName("Verificar que el Aceite SI tiene stock bajo")
    void checkAceiteStockBajo() {
        IngredienteResponseDTO ing = ingredienteService.obtenerPorId(2L);
        log.info("Stock bajo del Aceite: {}", ing.getStockBajo());
        assertTrue(ing.getStockBajo());
    }

    @Test
    @DisplayName("Verificar cantidad total de ingredientes")
    void checkCantidadIngredientes() {
        int cantidad = ingredienteService.listar().size();
        log.info("Total de ingredientes: {}", cantidad);
        assertEquals(3, cantidad);
    }

    @Test
    @DisplayName("Verificar ingredientes de la sede 6")
    void checkIngredientesPorSede() {
        int cantidad = ingredienteService.listarPorSede(6L).size();
        log.info("Ingredientes en sede 6: {}", cantidad);
        assertEquals(2, cantidad);
    }

    @Test
    @DisplayName("Verificar ingredientes con stock bajo")
    void checkStockBajo() {
        int cantidad = ingredienteService.listarStockBajo().size();
        log.info("Ingredientes con stock bajo: {}", cantidad);
        assertEquals(2, cantidad);
    }
}