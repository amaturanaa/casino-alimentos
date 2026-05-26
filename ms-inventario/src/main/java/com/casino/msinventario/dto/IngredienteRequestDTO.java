package com.casino.msinventario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO de entrada para crear o actualizar un ingrediente
// Separa los datos de entrada de la entidad JPA interna
// Bean Validation (JSR 380) valida los campos antes de llegar al Service
@Data
public class IngredienteRequestDTO {

    // Nombre descriptivo del ingrediente
    // @NotBlank valida que no sea nulo, vacío ni solo espacios en blanco
    // @Size limita la longitud máxima del campo en la base de datos
    // Ejemplo: "Arroz", "Aceite", "Sal", "Pollo"
    @NotBlank(message = "El nombre del ingrediente es obligatorio")
    @Size(max = 100)
    private String nombreIngrediente;

    // Identificador de la sede donde se almacena el ingrediente
    // @NotNull valida que no sea nulo
    // Se valida via Feign Client que la sede exista y esté operativa en ms-sucursales
    @NotNull
    private Long sedeId;

    // Unidad de medida del ingrediente
    // @NotBlank valida que no sea nulo, vacío ni solo espacios
    // Ejemplo: "kg", "litros", "unidades", "gramos"
    @NotBlank
    @Size(max = 20)
    private String unidadMedida;

    // Cantidad actual disponible del ingrediente en bodega
    // @Min valida que el valor sea mayor o igual a 0
    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock actual no puede ser negativo")
    private Double stockActual;

    // Cantidad mínima requerida del ingrediente
    // Si stockActual <= stockMinimo el sistema marca el ingrediente como stock bajo
    @NotNull
    @Min(0)
    private Double stockMinimo;
}