-- Transacciones (id explícito para que INSERT IGNORE evite duplicados al re-correr)
INSERT IGNORE INTO transaccion (id_transaccion, pedido_id, usuario_id, monto, metodo_pago, fecha_pago, estado_pago)
VALUES (1, 101, 1, 3500.0, 'JUNAEB', '2026-06-01 12:00:00', 'APROBADO');
INSERT IGNORE INTO transaccion (id_transaccion, pedido_id, usuario_id, monto, metodo_pago, fecha_pago, estado_pago)
VALUES (2, 102, 1, 4200.0, 'TARJETA_CREDITO', '2026-06-02 13:00:00', 'PENDIENTE');
INSERT IGNORE INTO transaccion (id_transaccion, pedido_id, usuario_id, monto, metodo_pago, fecha_pago, estado_pago)
VALUES (3, 103, 2, 2500.0, 'EFECTIVO', '2026-06-03 14:00:00', 'APROBADO');