-- Empleados (rut_empleado es unique, INSERT IGNORE evita duplicados al re-correr)
INSERT IGNORE INTO empleado (rut_empleado, dv_run_empleado, pnombre_empleado, snombre_empleado, appaterno_empleado, apmaterno_empleado, sueldo_base, cargo, jornada, activo, usuario_id)
VALUES ('11111111', '1', 'Juan', 'Carlos', 'Perez', 'Soto', 800000, 'Cocinero', 'COMPLETA', true, 1);

INSERT IGNORE INTO empleado (rut_empleado, dv_run_empleado, pnombre_empleado, snombre_empleado, appaterno_empleado, apmaterno_empleado, sueldo_base, cargo, jornada, activo, usuario_id)
VALUES ('22222222', '2', 'Maria', NULL, 'Gonzalez', NULL, 700000, 'Cajero', 'PARCIAL', true, 2);

INSERT IGNORE INTO empleado (rut_empleado, dv_run_empleado, pnombre_empleado, snombre_empleado, appaterno_empleado, apmaterno_empleado, sueldo_base, cargo, jornada, activo, usuario_id)
VALUES ('33333333', '3', 'Pedro', NULL, 'Lopez', NULL, 900000, 'Supervisor', 'COMPLETA', false, 3);

-- Turnos (id_turno explícito para que INSERT IGNORE evite duplicados al re-correr)
INSERT IGNORE INTO turno_empleado (id_turno, empleado_id, sede_id, fecha, hora_entrada, hora_salida, tipo_turno)
VALUES (1, 1, 6, '2026-06-01', '08:00:00', '17:00:00', 'TARDE');

INSERT IGNORE INTO turno_empleado (id_turno, empleado_id, sede_id, fecha, hora_entrada, hora_salida, tipo_turno)
VALUES (2, 1, 6, '2026-06-02', '14:00:00', '22:00:00', 'NOCHE');