package com.casino.msmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// DTO de salida para retornar datos de una programación diaria al cliente
// Evita exponer la entidad JPA directamente
// Incluye el nombre del plato para enriquecer la respuesta sin consultas adicionales
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramacionDiariaResponseDTO {

    // Identificador único de la programación generado por la base de datos
    private Long idProgramacion;

    // Fecha de la programación en formato ISO: yyyy-MM-dd
    // LocalDate almacena solo la fecha sin hora
    private LocalDate fecha;

    // Identificador de la sede donde se programa el menú
    // Referencia a ms-sucursales sin FK física entre bases de datos
    private Long sedeId;

    // Identificador del plato programado para esa fecha y sede
    // Referencia local a la entidad Plato dentro del mismo microservicio
    private Long platoId;

    // Nombre del plato programado — obtenido desde la entidad Plato
    // Evita que el cliente tenga que hacer una segunda consulta para obtener el nombre
    private String nombrePlato;

    // Cantidad de raciones disponibles restantes del plato para ese día
    // Se actualiza automáticamente al descontar raciones via pedidos
    // Cuando llega a 0 el plato ya no está disponible para ese día
    private Integer racionesDisponibles;
}