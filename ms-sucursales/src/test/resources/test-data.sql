-- Sedes de prueba (id explícito para controlar referencias)
INSERT IGNORE INTO sede_casino (id_sede_casino, nombre_sede, direccion, capacidad_comensales, hora_apertura, hora_cierre, estado_operativo)
VALUES (1, 'Casino Central', 'Av. Principal 123', 200, '08:00:00', '18:00:00', true);

INSERT IGNORE INTO sede_casino (id_sede_casino, nombre_sede, direccion, capacidad_comensales, hora_apertura, hora_cierre, estado_operativo)
VALUES (2, 'Casino Norte', 'Av. Norte 456', 150, '07:00:00', '17:00:00', true);

INSERT IGNORE INTO sede_casino (id_sede_casino, nombre_sede, direccion, capacidad_comensales, hora_apertura, hora_cierre, estado_operativo)
VALUES (3, 'Casino Sur', 'Av. Sur 789', 180, '09:00:00', '19:00:00', false);