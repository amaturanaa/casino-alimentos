package com.casino.msproveedores;

import com.casino.msproveedores.dto.OrdenCompraResponseDTO;
import com.casino.msproveedores.dto.ProveedorResponseDTO;
import com.casino.msproveedores.service.OrdenCompraService;
import com.casino.msproveedores.service.ProveedorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para ProveedorService y OrdenCompraService
// Solo se prueban métodos de lectura — OrdenCompraService.crear() usa Feign hacia ms-sucursales
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsProveedoresApplicationTests {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private OrdenCompraService ordenCompraService;

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