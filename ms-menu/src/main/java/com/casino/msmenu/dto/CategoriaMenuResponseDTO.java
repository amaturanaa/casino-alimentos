package com.casino.msmenu.dto;

import lombok.Data;

// DTO local para recibir la respuesta de ms-categorias-menu via Feign Client
// Es una copia simplificada del DTO de ms-categorias-menu
// Solo contiene los campos necesarios para la validación de categoría
// Cada microservicio que consume otro debe tener su propio DTO local
// Evita dependencias entre proyectos — cada MS es independiente
@Data
public class CategoriaMenuResponseDTO {

    // Identificador único de la categoría en ms-categorias-menu
    private Long id;

    // Nombre de la categoría para mostrar en mensajes de error
    // Ejemplo: "Almuerzo Completo", "Ensaladas", "Postres"
    private String nombre;

    // Estado de la categoría en ms-categorias-menu
    // true = activa (ms-menu puede crear platos con esta categoría)
    // false = inactiva (ms-menu rechaza crear platos con esta categoría)
    private Boolean estado;
}