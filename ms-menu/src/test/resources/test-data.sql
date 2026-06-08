-- Tipos de plato (nombre es unique, id explícito para INSERT IGNORE)
INSERT IGNORE INTO tipo_plato (id_tipo_plato, nombre_tipo_plato) VALUES (1, 'Plato de Fondo');
INSERT IGNORE INTO tipo_plato (id_tipo_plato, nombre_tipo_plato) VALUES (2, 'Entrada');
INSERT IGNORE INTO tipo_plato (id_tipo_plato, nombre_tipo_plato) VALUES (3, 'Postre');

-- Platos (id explícito, FK a tipo_plato)
INSERT IGNORE INTO platos (id_plato, nombre_plato, descripcion_plato, precio_referencial, tipo_plato_id, categoria_id, disponible)
VALUES (1, 'Pollo a la plancha', 'Pechuga de pollo con ensalada', 3500.0, 1, 1, true);
INSERT IGNORE INTO platos (id_plato, nombre_plato, descripcion_plato, precio_referencial, tipo_plato_id, categoria_id, disponible)
VALUES (2, 'Ensalada Cesar', 'Lechuga, crutones y aderezo cesar', 2500.0, 2, 2, true);
INSERT IGNORE INTO platos (id_plato, nombre_plato, descripcion_plato, precio_referencial, tipo_plato_id, categoria_id, disponible)
VALUES (3, 'Flan casero', 'Flan de huevo con caramelo', 1500.0, 3, 3, false);

-- Programaciones diarias (id explícito, FK a platos)
INSERT IGNORE INTO programacion_diaria (id_programacion, fecha, sede_id, plato_id, raciones_disponibles)
VALUES (1, '2026-06-10', 6, 1, 50);
INSERT IGNORE INTO programacion_diaria (id_programacion, fecha, sede_id, plato_id, raciones_disponibles)
VALUES (2, '2026-06-10', 7, 2, 30);