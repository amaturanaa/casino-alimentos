package com.casino.msempleados.service;

import com.casino.msempleados.dto.EmpleadoRequestDTO;
import com.casino.msempleados.dto.EmpleadoResponseDTO;
import com.casino.msempleados.model.Empleado;
import com.casino.msempleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Empleado
// Contiene toda la lógica de negocio del microservicio
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
    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) {
        log.info("Creando empleado con RUT: {}", dto.getRutEmpleado());

        // Validación de negocio: RUT único en el sistema
        if (empleadoRepository.existsByRutEmpleado(dto.getRutEmpleado())) {
            log.warn("Empleado ya existe con RUT: {}", dto.getRutEmpleado());
            throw new RuntimeException("El empleado ya existe con ese RUT");
        }

        // Construcción de la entidad desde el DTO de entrada
        Empleado empleado = new Empleado();
        empleado.setRutEmpleado(dto.getRutEmpleado());
        empleado.setDvRunEmpleado(dto.getDvRunEmpleado());
        empleado.setPnombreEmpleado(dto.getPnombreEmpleado());
        empleado.setSnombreEmpleado(dto.getSnombreEmpleado());
        empleado.setAppaternoEmpleado(dto.getAppaternoEmpleado());
        empleado.setApmaternoEmpleado(dto.getApmaternoEmpleado());
        empleado.setSueldoBase(dto.getSueldoBase());
        empleado.setCargo(dto.getCargo());
        empleado.setJornada(dto.getJornada());
        empleado.setActivo(true); // nuevo empleado siempre activo
        empleado.setUsuarioId(dto.getUsuarioId());

        // Persistencia en base de datos mediante JpaRepository
        Empleado guardado = empleadoRepository.save(empleado);
        log.info("Empleado creado con id: {}", guardado.getIdEmpleado());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToDTO(guardado);
    }

    @Override
    public EmpleadoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando empleado con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Empleado no encontrado con id: {}", id);
                    return new RuntimeException("Empleado no encontrado");
                });
        return mapToDTO(empleado);
    }

    @Override
    public EmpleadoResponseDTO obtenerPorRut(String rut) {
        log.info("Buscando empleado con RUT: {}", rut);

        Empleado empleado = empleadoRepository.findByRutEmpleado(rut)
                .orElseThrow(() -> {
                    log.error("Empleado no encontrado con RUT: {}", rut);
                    return new RuntimeException("Empleado no encontrado");
                });
        return mapToDTO(empleado);
    }

    @Override
    public List<EmpleadoResponseDTO> listar() {
        log.info("Listando todos los empleados");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findAll();
        for (Empleado e : empleados) {
            lista.add(mapToDTO(e));
        }
        log.info("Total empleados encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public List<EmpleadoResponseDTO> listarActivos() {
        log.info("Listando empleados activos");

        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findByActivo(true);
        for (Empleado e : empleados) {
            lista.add(mapToDTO(e));
        }
        log.info("Total empleados activos: {}", lista.size());
        return lista;
    }

    @Override
    public List<EmpleadoResponseDTO> listarPorCargo(String cargo) {
        log.info("Listando empleados por cargo: {}", cargo);

        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findByCargo(cargo);
        for (Empleado e : empleados) {
            lista.add(mapToDTO(e));
        }
        log.info("Total empleados con cargo {}: {}", cargo, lista.size());
        return lista;
    }

    @Override
    public EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto) {
        log.info("Actualizando empleado con id: {}", id);

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Empleado no encontrado para actualizar: {}", id);
                    return new RuntimeException("Empleado no encontrado");
                });

        // Actualiza solo los campos modificables — RUT no se puede cambiar
        empleado.setPnombreEmpleado(dto.getPnombreEmpleado());
        empleado.setSnombreEmpleado(dto.getSnombreEmpleado());
        empleado.setAppaternoEmpleado(dto.getAppaternoEmpleado());
        empleado.setApmaternoEmpleado(dto.getApmaternoEmpleado());
        empleado.setSueldoBase(dto.getSueldoBase());
        empleado.setCargo(dto.getCargo());
        empleado.setJornada(dto.getJornada());
        empleado.setUsuarioId(dto.getUsuarioId());

        Empleado actualizado = empleadoRepository.save(empleado);
        log.info("Empleado actualizado con id: {}", actualizado.getIdEmpleado());
        return mapToDTO(actualizado);
    }

    @Override
    public EmpleadoResponseDTO cambiarEstado(Long id, Boolean activo) {
        log.info("Cambiando estado de empleado {} a {}", id, activo);

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Empleado no encontrado para cambiar estado: {}", id);
                    return new RuntimeException("Empleado no encontrado");
                });

        // Baja lógica — no elimina el registro, solo cambia el estado
        empleado.setActivo(activo);
        Empleado actualizado = empleadoRepository.save(empleado);
        log.info("Estado de empleado {} actualizado a {}", id, activo);
        return mapToDTO(actualizado);
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Construye el nombre completo concatenando los campos de nombre
    private EmpleadoResponseDTO mapToDTO(Empleado e) {
        String nombreCompleto = e.getPnombreEmpleado();
        if (e.getSnombreEmpleado() != null) {
            nombreCompleto = nombreCompleto + " " + e.getSnombreEmpleado();
        }
        nombreCompleto = nombreCompleto + " " + e.getAppaternoEmpleado();
        if (e.getApmaternoEmpleado() != null) {
            nombreCompleto = nombreCompleto + " " + e.getApmaternoEmpleado();
        }

        return new EmpleadoResponseDTO(
                e.getIdEmpleado(),
                e.getRutEmpleado(),
                e.getDvRunEmpleado(),
                nombreCompleto.trim(),
                e.getCargo(),
                e.getJornada(),
                e.getSueldoBase(),
                e.getActivo(),
                e.getUsuarioId()
        );
    }
}