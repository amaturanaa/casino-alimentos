package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.dto.EmpleadoSedeResponseDTO;
import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import com.casino.mssucursales.repository.EmpleadoSedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoSedeServiceImpl implements EmpleadoSedeService {

    private final EmpleadoSedeRepository repository;

    @Override
    public EmpleadoSedeResponseDTO asignarEmpleadoASede(EmpleadoSedeRequestDTO request) {
        EmpleadoSedeId id = new EmpleadoSedeId(
                request.getIdEmpleado(),
                request.getIdSedeCasino()
        );

        if (repository.existsById(id)) {
            throw new RuntimeException("El empleado ya está asignado a esta sede");
        }

        EmpleadoSede empleadoSede = new EmpleadoSede();
        empleadoSede.setId(id);

        repository.save(empleadoSede);

        return new EmpleadoSedeResponseDTO(
                request.getIdEmpleado(),
                request.getIdSedeCasino()
        );
    }

    @Override
    public void eliminarAsignacion(Long idEmpleado, Long idSedeCasino) {
        repository.deleteById(new EmpleadoSedeId(idEmpleado, idSedeCasino));
    }

    @Override
    public List<EmpleadoSedeResponseDTO> listarPorSede(Long idSedeCasino) {
        return repository.findById_IdSedeCasino(idSedeCasino)
                .stream()
                .map(e -> new EmpleadoSedeResponseDTO(
                        e.getId().getIdEmpleado(),
                        e.getId().getIdSedeCasino()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoSedeResponseDTO> listarPorEmpleado(Long idEmpleado) {
        return repository.findById_IdEmpleado(idEmpleado)
                .stream()
                .map(e -> new EmpleadoSedeResponseDTO(
                        e.getId().getIdEmpleado(),
                        e.getId().getIdSedeCasino()
                ))
                .collect(Collectors.toList());
    }
}
