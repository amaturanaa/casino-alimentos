INSERT IGNORE INTO tipo_movimiento (nombre_tipo_movimiento)
VALUES ('ENTRADA');

INSERT IGNORE INTO tipo_movimiento (nombre_tipo_movimiento)
VALUES ('SALIDA');

INSERT IGNORE INTO tipo_movimiento (nombre_tipo_movimiento)
VALUES ('MERMA');

INSERT IGNORE INTO ingrediente (nombre_ingrediente, sede_id, unidad_medida, stock_actual, stock_minimo)
VALUES ('Arroz', 1, 'kg', 100.0, 20.0);

INSERT IGNORE INTO ingrediente (nombre_ingrediente, sede_id, unidad_medida, stock_actual, stock_minimo)
VALUES ('Aceite', 1, 'litros', 50.0, 10.0);

INSERT IGNORE INTO ingrediente (nombre_ingrediente, sede_id, unidad_medida, stock_actual, stock_minimo)
VALUES ('Sal', 1, 'kg', 30.0, 5.0);

INSERT IGNORE INTO ingrediente (nombre_ingrediente, sede_id, unidad_medida, stock_actual, stock_minimo)
VALUES ('Pollo', 1, 'kg', 80.0, 15.0);