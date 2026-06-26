package com.casino.mscategoriasmenu;

import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;
import com.casino.mscategoriasmenu.service.CategoriaMenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración con @SpringBootTest
// @SpringBootTest arranca la aplicación completa y se conecta a la BD de prueba
// @ActiveProfiles("test") fuerza el uso de application-test.properties (BD _test)
// @Slf4j genera el logger para registrar lo que verifica cada prueba
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsCategoriasMenuApplicationTests {

    // @Autowired inyecta el Service REAL desde el contexto de Spring
    @Autowired
    private CategoriaMenuService categoriaService;

    // Verifica que el contexto de Spring carga correctamente
    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar nombre de la categoría con id 1")
    void checkNombreCategoria() {
        CategoriaMenuResponseDTO categoria = categoriaService.obtenerPorId(1L);
        log.info("Revisando categoría {}", categoria.getNombre());
        assertEquals("Almuerzo Completo", categoria.getNombre());
    }

    @Test
    @DisplayName("Verificar que la categoría con id 1 está activa")
    void checkEstadoCategoria() {
        CategoriaMenuResponseDTO categoria = categoriaService.obtenerPorId(1L);
        log.info("Revisando estado de {}", categoria.getNombre());
        assertTrue(categoria.getEstado());
    }

    @Test
    @DisplayName("Verificar cantidad de categorías activas")
    void checkCategoriasActivas() {
        int activas = categoriaService.listarPorEstado(true).size();
        log.info("Verificación de categorías activas: {}", activas);
        assertEquals(5, activas);
    }
}