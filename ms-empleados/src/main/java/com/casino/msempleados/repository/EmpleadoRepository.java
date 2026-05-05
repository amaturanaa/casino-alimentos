package com.casino.msempleados.repository;

import com.casino.msempleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByRutEmpleado(String rutEmpleado);
    Optional<Empleado> findByRutEmpleado(String rutEmpleado);
    List<Empleado> findByActivo(Boolean activo);
    List<Empleado> findByCargo(String cargo);
}