package com.casino.msempleados.service;

import com.casino.msempleados.dto.EmpleadoRequestDTO;
import com.casino.msempleados.dto.EmpleadoResponseDTO;
import com.casino.msempleados.model.Empleado;
import com.casino.msempleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) {
        if (empleadoRepository.existsByRutEmpleado(dto.getRutEmpleado())) {
            throw new RuntimeException("El empleado ya existe con ese RUT");
        }

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
        empleado.setActivo(true);
        empleado.setUsuarioId(dto.getUsuarioId());

        return mapToDTO(empleadoRepository.save(empleado));
    }

    @Override
    public EmpleadoResponseDTO obtenerPorId(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return mapToDTO(empleado);
    }

    @Override
    public EmpleadoResponseDTO obtenerPorRut(String rut) {
        Empleado empleado = empleadoRepository.findByRutEmpleado(rut)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return mapToDTO(empleado);
    }

    @Override
    public List<EmpleadoResponseDTO> listar() {
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findAll();
        for (Empleado e : empleados) {
            lista.add(mapToDTO(e));
        }
        return lista;
    }

    @Override
    public List<EmpleadoResponseDTO> listarActivos() {
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findByActivo(true);
        for (Empleado e : empleados) {
            lista.add(mapToDTO(e));
        }
        return lista;
    }

    @Override
    public List<EmpleadoResponseDTO> listarPorCargo(String cargo) {
        List<EmpleadoResponseDTO> lista = new ArrayList<>();
        List<Empleado> empleados = empleadoRepository.findByCargo(cargo);
        for (Empleado e : empleados) {
            lista.add(mapToDTO(e));
        }
        return lista;
    }

    @Override
    public EmpleadoResponseDTO actualizar(Long id, EmpleadoRequestDTO dto) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.setPnombreEmpleado(dto.getPnombreEmpleado());
        empleado.setSnombreEmpleado(dto.getSnombreEmpleado());
        empleado.setAppaternoEmpleado(dto.getAppaternoEmpleado());
        empleado.setApmaternoEmpleado(dto.getApmaternoEmpleado());
        empleado.setSueldoBase(dto.getSueldoBase());
        empleado.setCargo(dto.getCargo());
        empleado.setJornada(dto.getJornada());
        empleado.setUsuarioId(dto.getUsuarioId());

        return mapToDTO(empleadoRepository.save(empleado));
    }

    @Override
    public EmpleadoResponseDTO cambiarEstado(Long id, Boolean activo) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        empleado.setActivo(activo);
        return mapToDTO(empleadoRepository.save(empleado));
    }

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