package com.casino.msinventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un ingrediente al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteResponseDTO {

    // Identificador único del ingrediente generado por la base de datos
    private Long idIngrediente;

    // Nombre descriptivo del ingrediente
    // Ejemplo: "Arroz", "Aceite", "Sal", "Pollo"
    private String nombreIngrediente;

    // Identificador de la sede donde se almacena el ingrediente
    // Referencia a ms-sucursales sin FK física entre bases de datos
    private Long sedeId;

    // Unidad de medida del ingrediente
    // Ejemplo: "kg", "litros", "unidades", "gramos"
    private String unidadMedida;

    // Cantidad actual disponible del ingrediente en bodega
    private Double stockActual;

    // Cantidad mínima requerida del ingrediente antes de generar alerta
    private Double stockMinimo;

    // Indicador calculado en el Service
    // true = stockActual <= stockMinimo (requiere reposición urgente)
    // false = stock suficiente
    private Boolean stockBajo;
}