package com.casino.mssucursales.service;

import com.casino.mssucursales.dto.EmpleadoSedeRequestDTO;
import com.casino.mssucursales.model.Empleado;
import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import com.casino.mssucursales.model.SedeCasino;
import com.casino.mssucursales.repository.EmpleadoRepository;
import com.casino.mssucursales.repository.EmpleadoSedeRepository;
import com.casino.mssucursales.repository.SedeCasinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpleadoSedeServiceImpl implements EmpleadoSedeService {

    private final EmpleadoRepository  empleadoRepository;
    private final SedeCasinoRepository sedeCasinoRepository;
    private final EmpleadoSedeRepository empleadoSedeRepository;

    @Override
    public void asignarEmpleadoASede(EmpleadoSedeRequestDTO dto) {

        Empleado empleado = empleadoRepository.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no existe"));

        SedeCasino sede = sedeCasinoRepository.findById(dto.getIdSedeCasino())
                .orElseThrow(() -> new RuntimeException("Sede no existe"));

        EmpleadoSedeId id = new EmpleadoSedeId(
                empleado.getIdEmpleado(),
                sede.getIdSedeCasino()
        );

        if (empleadoSedeRepository.existsById(id)) {
            throw new RuntimeException("El empleado ya está asignado a esta sede");
        }

        EmpleadoSede empleadoSede = new EmpleadoSede(id, empleado, sede);
        empleadoSedeRepository.save(empleadoSede);
    }
}
