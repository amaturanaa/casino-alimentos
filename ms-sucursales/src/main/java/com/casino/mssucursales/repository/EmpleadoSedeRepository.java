package com.casino.mssucursales.repository;

import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoSedeRepository extends JpaRepository<EmpleadoSede, EmpleadoSedeId> {
}
