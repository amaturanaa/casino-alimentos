INSERT IGNORE INTO categoria_menu (nombre, estado) VALUES ('Almuerzo Completo', true);
INSERT IGNORE INTO categoria_menu (nombre, estado) VALUES ('Ensaladas', true);
INSERT IGNORE INTO categoria_menu (nombre, estado) VALUES ('Postres', true);
INSERT IGNORE INTO categoria_menu (nombre, estado) VALUES ('Bebidas', true);
INSERT IGNORE INTO categoria_menu (nombre, estado) VALUES ('Minutas Especiales', true);

INSERT IGNORE INTO etiqueta_nutricional (categoria_id, calorias, proteinas, carbohidratos, grasas, es_vegetariano, es_vegano, contiene_gluten)
VALUES (1, 500, 25.0, 60.0, 15.0, false, false, true);