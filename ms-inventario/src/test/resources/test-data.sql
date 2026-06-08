-- Tipos de movimiento (nombre es unique, id explícito para INSERT IGNORE)
INSERT IGNORE INTO tipo_movimiento (id_tipo_movimiento, nombre_tipo_movimiento) VALUES (1, 'ENTRADA');
INSERT IGNORE INTO tipo_movimiento (id_tipo_movimiento, nombre_tipo_movimiento) VALUES (2, 'SALIDA');
INSERT IGNORE INTO tipo_movimiento (id_tipo_movimiento, nombre_tipo_movimiento) VALUES (3, 'MERMA');

-- Ingredientes (id explícito para controlar las referencias)
-- Arroz: stock 50, minimo 10 -> stock OK
INSERT IGNORE INTO ingrediente (id_ingrediente, nombre_ingrediente, sede_id, unidad_medida, stock_actual, stock_minimo)
VALUES (1, 'Arroz', 6, 'kg', 50.0, 10.0);
-- Aceite: stock 5, minimo 8 -> stock BAJO
INSERT IGNORE INTO ingrediente (id_ingrediente, nombre_ingrediente, sede_id, unidad_medida, stock_actual, stock_minimo)
VALUES (2, 'Aceite', 6, 'litros', 5.0, 8.0);
-- Sal: stock 3, minimo 3 -> stock BAJO (igual cuenta como bajo)
INSERT IGNORE INTO ingrediente (id_ingrediente, nombre_ingrediente, sede_id, unidad_medida, stock_actual, stock_minimo)
VALUES (3, 'Sal', 7, 'kg', 3.0, 3.0);

-- Movimientos de stock (id explícito, FK a ingrediente y tipo)
INSERT IGNORE INTO movimiento_stock (id_movimiento, ingrediente_id, tipo_movimiento_id, cantidad, fecha_movimiento, motivo)
VALUES (1, 1, 1, 20.0, '2026-06-01 10:00:00', 'Recepcion proveedor');
INSERT IGNORE INTO movimiento_stock (id_movimiento, ingrediente_id, tipo_movimiento_id, cantidad, fecha_movimiento, motivo)
VALUES (2, 1, 2, 5.0, '2026-06-02 12:00:00', 'Consumo cocina');