package com.casino.mssucursales.repository;

import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoSedeRepository extends JpaRepository<EmpleadoSede, EmpleadoSedeId> {

    List<EmpleadoSede> findById_IdSedeCasino(Long idSedeCasino);

    List<EmpleadoSede> findById_IdEmpleado(Long idEmpleado);

}
