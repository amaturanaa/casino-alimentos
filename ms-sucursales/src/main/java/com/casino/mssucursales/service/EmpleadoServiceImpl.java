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

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public EmpleadoResponseDTO crearEmpleado(EmpleadoRequestDTO dto) {

        if (empleadoRepository.existsByRunEmpleado(dto.getRunEmpleado())) {
            throw new RuntimeException("El empleado ya existe");
        }

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

        return mapToResponse(empleadoRepository.save(empleado));
    }

    @Override
    public List<EmpleadoResponseDTO> listarEmpleados() {
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findAll();

        for (Empleado e : empleados) {
            lista.add(mapToResponse(e));
        }
        return lista;
    }

    private EmpleadoResponseDTO mapToResponse(Empleado empleado) {
        String nombreCompleto = empleado.getPnombreEmpleado() + " " +
                (empleado.getSnombreEmpleado() != null ? empleado.getSnombreEmpleado() + " " : "") +
                empleado.getAppaternoEmpleado() + " " +
                (empleado.getApmaternoEmpleado() != null ? empleado.getApmaternoEmpleado() : "");

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