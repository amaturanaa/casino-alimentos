package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoRequestDTO;
import com.casino.mssucursales.dto.EmpleadoResponseDTO;
import com.casino.mssucursales.model.Empleado;
import com.casino.mssucursales.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Empleado en ms-sucursales
// Contiene la lógica de negocio relacionada a empleados de sucursal
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    // Repositorio JPA inyectado mediante constructor
    private final EmpleadoRepository empleadoRepository;

    @Override
    public EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto) {
        log.info("Creando empleado con RUN: {}", dto.getRunEmpleado());

        // Validación de negocio: RUN único en el sistema
        if (empleadoRepository.existsByRunEmpleado(dto.getRunEmpleado())) {
            log.warn("Empleado ya existe con RUN: {}", dto.getRunEmpleado());
            throw new RuntimeException("El empleado ya existe");
        }

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        Empleado empleado = new Empleado(
                null,
                dto.getRunEmpleado(),
                dto.getDvRunEmpleado(),
                dto.getPnombreEmpleado(),
                dto.getSnombreEmpleado(),
                dto.getAppaternoEmpleado(),
                dto.getApmaternoEmpleado(),
                dto.getSueldoBase(),
                dto.getCargo(),
                dto.getJornada()
        );

        // Persistencia en base de datos mediante JpaRepository
        Empleado guardado = empleadoRepository.save(empleado);
        log.info("Empleado creado con id: {}", guardado.getIdEmpleado());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToResponse(guardado);
    }

    @Override
    public List<EmpleadoResponseDTO> listarEmpleados() {
        log.info("Listando todos los empleados de sucursal");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findAll();

        for (Empleado e : empleados) {
            lista.add(mapToResponse(e));
        }

        log.info("Total empleados encontrados: {}", lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Construye el nombre completo concatenando los campos de nombre
    // Los campos opcionales (snombre, apmaterno) se incluyen solo si no son null
    private EmpleadoResponseDTO mapToResponse(Empleado empleado) {
        String nombreCompleto = empleado.getPnombreEmpleado() + " " +
                (empleado.getSnombreEmpleado() != null
                        ? empleado.getSnombreEmpleado() + " " : "") +
                empleado.getAppaternoEmpleado() + " " +
                (empleado.getApmaternoEmpleado() != null
                        ? empleado.getApmaternoEmpleado() : "");

        return new EmpleadoResponseDTO(
                empleado.getIdEmpleado(),
                empleado.getRunEmpleado(),
                nombreCompleto.trim(),
                empleado.getCargo(),
                empleado.getJornada(),
                empleado.getSueldoBase()
        );
    }
}