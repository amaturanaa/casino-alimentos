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

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolRepository rolRepository;

    @PostMapping
    public ResponseEntity<Rol> crearRol(@Valid @RequestBody RolRequestDTO dto) {
        Rol rol = new Rol();
        rol.setNombre_rol(dto.getNombre_rol());
        rol.setDescripcion(dto.getDescripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(rolRepository.save(rol));
    }

    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolRepository.findAll());
    }
}
