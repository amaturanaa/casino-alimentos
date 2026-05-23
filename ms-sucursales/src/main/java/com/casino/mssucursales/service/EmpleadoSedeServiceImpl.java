package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.dto.EmpleadoSedeResponseDTO;
import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import com.casino.mssucursales.repository.EmpleadoSedeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        List<EmpleadoSedeResponseDTO> lista = new ArrayList<>();
        List<EmpleadoSede> empleadosSede = repository.findById_IdSedeCasino(idSedeCasino);

        for (EmpleadoSede e : empleadosSede) {
            EmpleadoSedeResponseDTO dto = new EmpleadoSedeResponseDTO(
                    e.getId().getIdEmpleado(),
                    e.getId().getIdSedeCasino()
            );
            lista.add(dto);
        }
        return lista;
    }

    @Override
    public List<EmpleadoSedeResponseDTO> listarPorEmpleado(Long idEmpleado) {
        List<EmpleadoSedeResponseDTO> lista = new ArrayList<>();
        List<EmpleadoSede> sedesEmpleado = repository.findById_IdEmpleado(idEmpleado);

        for (EmpleadoSede e : sedesEmpleado) {
            EmpleadoSedeResponseDTO dto = new EmpleadoSedeResponseDTO(
                    e.getId().getIdEmpleado(),
                    e.getId().getIdSedeCasino()
            );
            lista.add(dto);
        }
        return lista;
    }
}