package com.casino.mspedidos;

import com.casino.mspedidos.dto.PedidoResponseDTO;
import com.casino.mspedidos.service.PedidoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración para PedidoService
@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class MsPedidosApplicationTests {

    @Autowired
    private PedidoService pedidoService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Verificar estado del pedido con id 1")
    void checkEstadoPedido() {
        PedidoResponseDTO pedido = pedidoService.obtenerPorId(1L);
        log.info("Estado del pedido 1: {}", pedido.getEstado());
        assertEquals("RECIBIDO", pedido.getEstado());
    }

    @Test
    @DisplayName("Verificar pedidos del usuario 4 (Valentina)")
    void checkPedidosPorUsuario() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPorUsuario(4L);
        log.info("Pedidos del usuario 4: {}", pedidos.size());
        assertEquals(1, pedidos.size());
    }

    @Test
    @DisplayName("Verificar pedidos filtrados por estado ENTREGADO")
    void checkPedidosPorEstado() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPorEstado("ENTREGADO");
        log.info("Pedidos ENTREGADO: {}", pedidos.size());
        assertEquals(1, pedidos.size());
    }
}