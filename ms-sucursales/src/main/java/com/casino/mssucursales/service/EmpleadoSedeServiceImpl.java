package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.dto.EmpleadoSedeResponseDTO;
import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import com.casino.mssucursales.repository.EmpleadoSedeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la relación EmpleadoSede
// Contiene la lógica de negocio para gestionar asignaciones empleado-sede
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class EmpleadoSedeServiceImpl implements EmpleadoSedeService {

    // Repositorio JPA para acceso a datos de EmpleadoSede
    // Usa clave primaria compuesta EmpleadoSedeId
    private final EmpleadoSedeRepository repository;

    @Override
    public EmpleadoSedeResponseDTO asignarEmpleadoASede(EmpleadoSedeRequestDTO request) {
        log.info("Asignando empleado: {} a sede: {}",
                request.getIdEmpleado(), request.getIdSedeCasino());

        // Construcción de la clave primaria compuesta
        EmpleadoSedeId id = new EmpleadoSedeId(
                request.getIdEmpleado(),
                request.getIdSedeCasino()
        );

        // Validación de negocio: un empleado no puede estar asignado dos veces a la misma sede
        if (repository.existsById(id)) {
            log.warn("Empleado {} ya está asignado a sede {}",
                    request.getIdEmpleado(), request.getIdSedeCasino());
            throw new RuntimeException("El empleado ya está asignado a esta sede");
        }

        // Construcción de la entidad EmpleadoSede con la clave compuesta
        EmpleadoSede empleadoSede = new EmpleadoSede();
        empleadoSede.setId(id);

        // Persistencia en base de datos mediante JpaRepository
        repository.save(empleadoSede);
        log.info("Empleado {} asignado exitosamente a sede {}",
                request.getIdEmpleado(), request.getIdSedeCasino());

        return new EmpleadoSedeResponseDTO(
                request.getIdEmpleado(),
                request.getIdSedeCasino()
        );
    }

    @Override
    public void eliminarAsignacion(Long idEmpleado, Long idSedeCasino) {
        log.info("Eliminando asignación de empleado: {} en sede: {}", idEmpleado, idSedeCasino);

        // Construye la clave compuesta y elimina el registro correspondiente
        repository.deleteById(new EmpleadoSedeId(idEmpleado, idSedeCasino));
        log.info("Asignación eliminada exitosamente");
    }

    @Override
    public List<EmpleadoSedeResponseDTO> listarPorSede(Long idSedeCasino) {
        log.info("Listando empleados para sede: {}", idSedeCasino);

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<EmpleadoSedeResponseDTO> lista = new ArrayList<>();
        List<EmpleadoSede> empleadosSede = repository.findById_IdSedeCasino(idSedeCasino);

        for (EmpleadoSede e : empleadosSede) {
            // Accede a los ids a través de la clave compuesta embebida
            EmpleadoSedeResponseDTO dto = new EmpleadoSedeResponseDTO(
                    e.getId().getIdEmpleado(),
                    e.getId().getIdSedeCasino()
            );
            lista.add(dto);
        }

        log.info("Total empleados en sede {}: {}", idSedeCasino, lista.size());
        return lista;
    }

    @Override
    public List<EmpleadoSedeResponseDTO> listarPorEmpleado(Long idEmpleado) {
        log.info("Listando sedes para empleado: {}", idEmpleado);

        List<EmpleadoSedeResponseDTO> lista = new ArrayList<>();
        List<EmpleadoSede> sedesEmpleado = repository.findById_IdEmpleado(idEmpleado);

        for (EmpleadoSede e : sedesEmpleado) {
            EmpleadoSedeResponseDTO dto = new EmpleadoSedeResponseDTO(
                    e.getId().getIdEmpleado(),
                    e.getId().getIdSedeCasino()
            );
            lista.add(dto);
        }

        log.info("Total sedes para empleado {}: {}", idEmpleado, lista.size());
        return lista;
    }
}