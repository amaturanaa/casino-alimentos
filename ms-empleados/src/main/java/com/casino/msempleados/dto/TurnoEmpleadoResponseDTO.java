package com.casino.msempleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

// DTO de salida para retornar datos de un turno de empleado al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnoEmpleadoResponseDTO {

    // Identificador único del turno generado por la base de datos
    private Long idTurno;

    // Identificador del empleado asignado al turno
    private Long idEmpleado;

    // Nombre completo del empleado construido en el Service
    // Combina: pnombre + appaterno del empleado
    private String nombreEmpleado;

    // Identificador de la sede donde se realiza el turno
    // Referencia a ms-sucursales sin FK física entre bases de datos
    private Long sedeId;

    // Fecha del turno en formato ISO: yyyy-MM-dd
    private LocalDate fecha;

    // Hora de entrada al turno en formato HH:mm:ss
    private LocalTime horaEntrada;

    // Hora de salida del turno en formato HH:mm:ss
    private LocalTime horaSalida;

    // Tipo de turno laboral asignado
    // Ejemplo: MAÑANA, TARDE, NOCHE
    private String tipoTurno;
}