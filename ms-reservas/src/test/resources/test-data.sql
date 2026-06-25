-- Turnos disponibles (id explícito, orden importa: turnos antes de reservas)
INSERT IGNORE INTO turno_disponible (id_turno, sede_id, fecha, hora_inicio, hora_fin, capacidad, cupos_restantes)
VALUES (1, 6, '2026-06-23', '12:00:00', '13:00:00', 50, 48);

INSERT IGNORE INTO turno_disponible (id_turno, sede_id, fecha, hora_inicio, hora_fin, capacidad, cupos_restantes)
VALUES (2, 4, '2026-06-23', '13:00:00', '14:00:00', 40, 40);

INSERT IGNORE INTO turno_disponible (id_turno, sede_id, fecha, hora_inicio, hora_fin, capacidad, cupos_restantes)
VALUES (3, 5, '2026-06-23', '19:00:00', '20:00:00', 60, 0);

-- Reservas (id explícito, FK a turno_disponible)
INSERT IGNORE INTO reserva (id_reserva, usuario_id, turno_id, sede_id, fecha_creacion, estado)
VALUES (1, 1, 1, 6, '2026-06-21 10:00:00', 'ACTIVA');

INSERT IGNORE INTO reserva (id_reserva, usuario_id, turno_id, sede_id, fecha_creacion, estado)
VALUES (2, 2, 1, 6, '2026-06-21 11:00:00', 'ACTIVA');

INSERT IGNORE INTO reserva (id_reserva, usuario_id, turno_id, sede_id, fecha_creacion, estado)
VALUES (3, 4, 2, 4, '2026-06-21 12:00:00', 'CANCELADA');