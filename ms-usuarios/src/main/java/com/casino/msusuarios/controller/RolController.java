package com.casino.msusuarios.controller;

import com.casino.msusuarios.dto.RolRequestDTO;
import com.casino.msusuarios.model.Rol;
import com.casino.msusuarios.repository.RolRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador REST que expone los endpoints del recurso Rol
// Sigue el patrón CSR: solo orquesta llamadas al Repository, sin lógica de negocio
// Los roles definen los permisos de acceso de los usuarios del sistema
// Ejemplo: ROLE_ADMIN, ROLE_OPERADOR, ROLE_CLIENTE
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    // Inyección directa del repositorio ya que la lógica de Rol es simple
    // No requiere Service porque no hay lógica de negocio compleja
    private final RolRepository rolRepository;

    // POST /api/roles — Crea un nuevo rol en el sistema
    // @Valid activa Bean Validation sobre el DTO antes de procesar
    // HttpStatus.CREATED (201) indica creación exitosa de un nuevo recurso
    // Retorna la entidad Rol directamente por simplicidad
    @PostMapping
    public ResponseEntity<Rol> crearRol(@Valid @RequestBody RolRequestDTO dto) {
        Rol rol = new Rol();
        rol.setNombreRol(dto.getNombre_rol());
        rol.setDescripcion(dto.getDescripcion());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rolRepository.save(rol));
    }

    // GET /api/roles — Lista todos los roles del sistema
    // ResponseEntity.ok() retorna código HTTP 200
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolRepository.findAll());
    }
}