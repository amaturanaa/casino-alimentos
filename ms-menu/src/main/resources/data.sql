INSERT IGNORE INTO tipo_plato (nombre_tipo_plato)
VALUES ('Plato de Fondo');

INSERT IGNORE INTO tipo_plato (nombre_tipo_plato)
VALUES ('Entrada');

INSERT IGNORE INTO tipo_plato (nombre_tipo_plato)
VALUES ('Postre');

INSERT IGNORE INTO tipo_plato (nombre_tipo_plato)
VALUES ('Bebida');

INSERT IGNORE INTO plato (nombre_plato, descripcion_plato, precio_referencial, tipo_plato_id, categoria_id, disponible)
VALUES ('Pollo a la plancha', 'Pechuga de pollo con ensalada', 3500, 1, 1, true);

INSERT IGNORE INTO plato (nombre_plato, descripcion_plato, precio_referencial, tipo_plato_id, categoria_id, disponible)
VALUES ('Cazuela de vacuno', 'Cazuela tradicional chilena', 4000, 1, 1, true);

INSERT IGNORE INTO plato (nombre_plato, descripcion_plato, precio_referencial, tipo_plato_id, categoria_id, disponible)
VALUES ('Ensalada mixta', 'Lechuga, tomate y pepino', 1500, 2, 2, true);