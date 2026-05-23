package com.casino.msempleados.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de salida para retornar datos de un empleado al cliente
// Evita exponer la entidad JPA directamente
// Solo contiene los campos necesarios para la respuesta REST
// @AllArgsConstructor genera constructor con todos los campos
// @NoArgsConstructor genera constructor vacío requerido por Jackson para deserializar
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponseDTO {

    // Identificador único del empleado generado por la base de datos
    private Long idEmpleado;

    // RUT del empleado sin dígito verificador
    private String rutEmpleado;

    // Dígito verificador del RUT
    private String dvRunEmpleado;

    // Nombre completo construido en el Service
    // Combina: pnombre + snombre + appaterno + apmaterno
    private String nombreCompleto;

    // Cargo actual del empleado en el casino
    private String cargo;

    // Tipo de jornada laboral del empleado
    private String jornada;

    // Sueldo base mensual del empleado
    private Integer sueldoBase;

    // Estado del empleado en el sistema
    // true = activo, false = inactivo (baja lógica)
    private Boolean activo;

    // Referencia al usuario del sistema (ms-usuarios)
    // Puede ser null si el empleado no tiene usuario asociado
    private Long usuarioId;
}