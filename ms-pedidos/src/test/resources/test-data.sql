-- Pedidos (id explícito)
INSERT IGNORE INTO pedido (id_pedido, usuario_id, sede_id, fecha_hora, estado, total_pedido)
VALUES (1, 1, 6, '2026-06-21 12:00:00', 'RECIBIDO', 7000.0);

INSERT IGNORE INTO pedido (id_pedido, usuario_id, sede_id, fecha_hora, estado, total_pedido)
VALUES (2, 2, 4, '2026-06-21 13:00:00', 'EN_PREPARACION', 3800.0);

INSERT IGNORE INTO pedido (id_pedido, usuario_id, sede_id, fecha_hora, estado, total_pedido)
VALUES (3, 4, 5, '2026-06-21 14:00:00', 'ENTREGADO', 5800.0);

-- Detalles (id explícito, FK a pedido)
INSERT IGNORE INTO detalle_pedido (id_detalle_pedido, pedido_id, plato_id, cantidad, sub_total)
VALUES (1, 1, 1, 2, 7000.0);

INSERT IGNORE INTO detalle_pedido (id_detalle_pedido, pedido_id, plato_id, cantidad, sub_total)
VALUES (2, 2, 5, 1, 3800.0);

INSERT IGNORE INTO detalle_pedido (id_detalle_pedido, pedido_id, plato_id, cantidad, sub_total)
VALUES (3, 3, 2, 1, 2500.0);

INSERT IGNORE INTO detalle_pedido (id_detalle_pedido, pedido_id, plato_id, cantidad, sub_total)
VALUES (4, 3, 3, 1, 1500.0);

INSERT IGNORE INTO detalle_pedido (id_detalle_pedido, pedido_id, plato_id, cantidad, sub_total)
VALUES (5, 3, 6, 1, 1800.0);