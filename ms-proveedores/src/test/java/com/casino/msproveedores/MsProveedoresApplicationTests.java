package com.casino.msproveedores;

import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;
import com.casino.msproveedores.service.OrdenCompraService;
import com.casino.msproveedores.service.ProveedorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MsProveedoresApplicationTests {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private OrdenCompraService ordenCompraService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    void cargarDatos() {
        jdbcTemplate.execute("INSERT IGNORE INTO proveedor (id_proveedor, rut_proveedor, dv_run_proveedor, razon_social, email_proveedor, telefono, activo) VALUES (1, '12345678', '9', 'Distribuidora Sur Ltda', 'contacto@sur.cl', '987654321', true)");
        jdbcTemplate.execute("INSERT IGNORE INTO proveedor (id_proveedor, rut_proveedor, dv_run_proveedor, razon_social, email_proveedor, telefono, activo) VALUES (2, '87654321', 'K', 'Alimentos del Norte SA', 'ventas@norte.cl', '912345678', true)");
        jdbcTemplate.execute("INSERT IGNORE INTO proveedor (id_proveedor, rut_proveedor, dv_run_proveedor, razon_social, email_proveedor, telefono, activo) VALUES (3, '11111111', '1', 'Comercial Central Ltda', 'info@central.cl', '956789012', false)");
        jdbcTemplate.execute("INSERT IGNORE INTO orden_compra (id_orden_compra, proveedor_id, sede_id, fecha_solicitud, estado, costo_total) VALUES (1, 1, 6, '2026-06-21 10:00:00', 'PENDIENTE', 210000.0)");
        jdbcTemplate.execute("INSERT IGNORE INTO orden_compra (id_orden_compra, proveedor_id, sede_id, fecha_solicitud, estado, costo_total) VALUES (2, 2, 4, '2026-06-21 11:00:00', 'RECIBIDA', 416000.0)");
        jdbcTemplate.execute("INSERT IGNORE INTO orden_compra (id_orden_compra, proveedor_id, sede_id, fecha_solicitud, estado, costo_total) VALUES (3, 3, 5, '2026-06-21 12:00:00', 'CANCELADA', 54000.0)");
        log.info("Datos de prueba cargados manualmente");
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar razon social del proveedor con id 1")
    void checkRazonSocialProveedor() {
        ProveedorResponseDTO proveedor = proveedorService.obtenerPorId(1L);
        log.info("Revisando proveedor {}", proveedor.getRazonSocial());
        assertEquals("Distribuidora Sur Ltda", proveedor.getRazonSocial());
    }

    @Test
    @DisplayName("Verificar proveedores activos")
    void checkProveedoresActivos() {
        List<ProveedorResponseDTO> activos = proveedorService.listarActivos();
        log.info("Proveedores activos: {}", activos.size());
        assertEquals(2, activos.size());
    }

    @Test
    @DisplayName("Verificar ordenes filtradas por estado PENDIENTE")
    void checkOrdenesPorEstado() {
        List<OrdenCompraResponseDTO> ordenes = ordenCompraService.listarPorEstado("PENDIENTE");
        log.info("Ordenes PENDIENTE: {}", ordenes.size());
        assertEquals(1, ordenes.size());
    }
}