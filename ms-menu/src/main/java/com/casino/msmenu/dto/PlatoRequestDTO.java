package com.casino.msmenu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear un plato del menú
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
// Al crear un plato se verifica la categoría via Feign con ms-categorias-menu
@Data
public class PlatoRequestDTO {

    // Nombre descriptivo del plato
    // @NotBlank valida que no sea nulo, vacío ni solo espacios en blanco
    // @Size limita la longitud máxima del campo en la base de datos
    // Ejemplo: "Pollo a la plancha", "Cazuela de vacuno", "Ensalada mixta"
    @NotBlank(message = "El nombre del plato es obligatorio")
    @Size(max = 100, message = "El nombre del plato no puede superar los 100 caracteres")
    private String nombrePlato;

    // Descripción detallada del plato — campo opcional
    // @Size limita la longitud máxima del campo en la base de datos
    // Ejemplo: "Pechuga de pollo con ensalada y arroz"
    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String descripcionPlato;

    // Precio referencial del plato en pesos chilenos
    // @NotNull valida que no sea nulo
    // @Min valida que el valor sea mayor o igual a 0
    @NotNull(message = "El precio referencial es obligatorio")
    @Min(value = 0, message = "El precio referencial no puede ser negativo")
    private Double precioReferencial;

    // Identificador del tipo de plato al que pertenece
    // @NotNull valida que no sea nulo
    // Referencia local a la entidad TipoPlato dentro del mismo microservicio
    // Ejemplo: 1=Plato de Fondo, 2=Entrada, 3=Postre, 4=Bebida
    @NotNull(message = "El tipo de plato es obligatorio")
    private Long tipoPlatoId;

    // Identificador de la categoría del menú en ms-categorias-menu
    // @NotNull valida que no sea nulo
    // Se valida via Feign Client que la categoría exista y esté activa
    @NotNull(message = "La categoría del menú es obligatoria")
    private Long categoriaId;
}