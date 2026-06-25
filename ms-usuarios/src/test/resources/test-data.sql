-- Roles (id explícito, nombre es unique)
INSERT IGNORE INTO rol (id_rol, nombre_rol, descripcion)
VALUES (1, 'ROLE_ADMIN', 'Administrador del sistema');

INSERT IGNORE INTO rol (id_rol, nombre_rol, descripcion)
VALUES (2, 'ROLE_OPERADOR', 'Operador de casino');

INSERT IGNORE INTO rol (id_rol, nombre_rol, descripcion)
VALUES (3, 'ROLE_CLIENTE', 'Cliente del casino');

-- Usuarios con contraseñas encriptadas con BCrypt
-- Admin123! encriptado
INSERT IGNORE INTO usuario (rut_usuario, pnombre_usuario, snombre_usuario, appaterno_usuario, apmaterno_usuario, email, password, activo, rol_id)
VALUES ('11111111', 'Andrea', 'Paz', 'Soto', 'Reyes', 'admin@casino.cl', '$2b$12$PUUBHZf4fl56rJQLNhtvvu03c1.0gTWnbo.24YSKPYI4oMObAZLsG', true, 1);

-- Operador123! encriptado
INSERT IGNORE INTO usuario (rut_usuario, pnombre_usuario, snombre_usuario, appaterno_usuario, apmaterno_usuario, email, password, activo, rol_id)
VALUES ('22222222', 'Cristian', null, 'Munoz', 'Diaz', 'operador@casino.cl', '$2b$12$8xKsJ4JQ72FyrPVu70H.D.bp0rlPgpaSHRl3bKFTA.68WLCBEf3zW', true, 2);

-- Cliente123! encriptado (activo=false para probar baja lógica)
INSERT IGNORE INTO usuario (rut_usuario, pnombre_usuario, snombre_usuario, appaterno_usuario, apmaterno_usuario, email, password, activo, rol_id)
VALUES ('33333333', 'Valentina', null, 'Lopez', 'Vega', 'cliente@casino.cl', '$2b$12$no.I7D.k7eM96Wd80nRz5eFYmeIc9FJyiCMtnK.kOr.dVKfvjox5q', false, 3);