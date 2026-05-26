package com.casino.msmenu.service;

import com.casino.msmenu.dto.ProgramacionDiariaRequestDTO;
import com.casino.msmenu.dto.ProgramacionDiariaResponseDTO;
import java.time.LocalDate;
import java.util.List;

// Interfaz del Service para la entidad ProgramacionDiaria
// Define el contrato de operaciones disponibles sin exponer la implementación
// El Controller solo conoce esta interfaz, no la clase ProgramacionDiariaServiceImpl
// Permite cambiar la implementación sin modificar el Controller (principio SOLID)
public interface ProgramacionDiariaService {

    // Crea una nueva programación diaria para un plato en una sede y fecha
    // Lanza RuntimeException si el plato no existe o no está disponible
    ProgramacionDiariaResponseDTO crear(ProgramacionDiariaRequestDTO dto);

    // Lista todas las programaciones de una fecha específica
    // Útil para ver el menú completo de un día en todas las sedes
    List<ProgramacionDiariaResponseDTO> listarPorFecha(LocalDate fecha);

    // Lista todas las programaciones de una sede específica
    // sedeId es referencia a ms-sucursales sin FK física
    List<ProgramacionDiariaResponseDTO> listarPorSede(Long sedeId);

    // Lista programaciones filtradas por fecha y sede simultáneamente
    // Útil para ver el menú del día de una sede específica
    List<ProgramacionDiariaResponseDTO> listarPorFechaYSede(LocalDate fecha, Long sedeId);

    // Descuenta una ración de la programación al procesar un pedido
    // Lanza RuntimeException si no hay raciones disponibles
    // Lanza RuntimeException si la programación no existe
    ProgramacionDiariaResponseDTO descontarRacion(Long id);
}