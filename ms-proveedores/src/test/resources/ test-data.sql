-- Proveedores (id explícito, rut es unique)
INSERT IGNORE INTO proveedor (id_proveedor, rut_proveedor, dv_run_proveedor, razon_social, email_proveedor, telefono, activo)
VALUES (1, '12345678', '9', 'Distribuidora Sur Ltda', 'contacto@sur.cl', '987654321', true);

INSERT IGNORE INTO proveedor (id_proveedor, rut_proveedor, dv_run_proveedor, razon_social, email_proveedor, telefono, activo)
VALUES (2, '87654321', 'K', 'Alimentos del Norte SA', 'ventas@norte.cl', '912345678', true);

INSERT IGNORE INTO proveedor (id_proveedor, rut_proveedor, dv_run_proveedor, razon_social, email_proveedor, telefono, activo)
VALUES (3, '11111111', '1', 'Comercial Central Ltda', 'info@central.cl', '956789012', false);

-- Ordenes de compra (id explícito, FK a proveedor)
INSERT IGNORE INTO orden_compra (id_orden_compra, proveedor_id, sede_id, fecha_solicitud, estado, costo_total)
VALUES (1, 1, 6, '2026-06-21 10:00:00', 'PENDIENTE', 210000.0);

INSERT IGNORE INTO orden_compra (id_orden_compra, proveedor_id, sede_id, fecha_solicitud, estado, costo_total)
VALUES (2, 2, 4, '2026-06-21 11:00:00', 'RECIBIDA', 416000.0);

INSERT IGNORE INTO orden_compra (id_orden_compra, proveedor_id, sede_id, fecha_solicitud, estado, costo_total)
VALUES (3, 3, 5, '2026-06-21 12:00:00', 'CANCELADA', 54000.0);