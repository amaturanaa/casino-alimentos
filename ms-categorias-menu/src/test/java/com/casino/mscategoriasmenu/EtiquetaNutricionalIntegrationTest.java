package com.casino.mscategoriasmenu;

import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;
import com.casino.mscategoriasmenu.service.EtiquetaNutricionalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para EtiquetaNutricionalService
// @SpringBootTest arranca la app completa y se conecta a la BD de prueba
// @ActiveProfiles("test") fuerza el uso de application-test.properties
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class EtiquetaNutricionalIntegrationTest {

    // @Autowired inyecta el Service REAL desde el contexto de Spring
    @Autowired
    private EtiquetaNutricionalService etiquetaService;

    @Test
    @DisplayName("Verificar las calorías de la etiqueta de la categoría 1")
    void checkCaloriasEtiqueta() {
        EtiquetaNutricionalResponseDTO etiqueta = etiquetaService.obtenerPorCategoria(1L);
        log.info("Calorías de la etiqueta: {}", etiqueta.getCalorias());
        assertEquals(500, etiqueta.getCalorias().intValue());
    }

}