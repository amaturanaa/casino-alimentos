package com.casino.mscategoriasmenu.service;

import com.casino.mscategoriasmenu.dto.CategoriaMenuRequestDTO;
import com.casino.mscategoriasmenu.dto.CategoriaMenuResponseDTO;
import com.casino.mscategoriasmenu.model.CategoriaMenu;
import com.casino.mscategoriasmenu.repository.CategoriaMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad CategoriaMenu
// Contiene toda la lógica de negocio relacionada a categorías de menú
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaMenuServiceImpl implements CategoriaMenuService {

    // Repositorio JPA inyectado mediante constructor
    private final CategoriaMenuRepository repository;

    @Override
    public CategoriaMenuResponseDTO crear(CategoriaMenuRequestDTO request) {
        log.info("Creando categoría: {}", request.getNombre());

        // Validación de negocio: nombre único en el sistema
        if (repository.existsByNombre(request.getNombre())) {
            log.warn("Categoría ya existe: {}", request.getNombre());
            throw new RuntimeException("La categoría ya existe");
        }

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        CategoriaMenu categoria = new CategoriaMenu(
                null,
                request.getNombre(),
                request.getEstado()
        );

        // Persistencia en base de datos mediante JpaRepository
        CategoriaMenu guardada = repository.save(categoria);
        log.info("Categoría creada con id: {}", guardada.getId());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return new CategoriaMenuResponseDTO(
                guardada.getId(),
                guardada.getNombre(),
                guardada.getEstado()
        );
    }

    @Override
    public List<CategoriaMenuResponseDTO> listar() {
        log.info("Listando todas las categorías");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<CategoriaMenuResponseDTO> lista = new ArrayList<>();
        List<CategoriaMenu> categorias = repository.findAll();

        for (CategoriaMenu categoria : categorias) {
            CategoriaMenuResponseDTO dto = new CategoriaMenuResponseDTO(
                    categoria.getId(),
                    categoria.getNombre(),
                    categoria.getEstado()
            );
            lista.add(dto);
        }

        log.info("Total categorías encontradas: {}", lista.size());
        return lista;
    }

    @Override
    public CategoriaMenuResponseDTO obtenerPorId(Long id) {
        log.info("Buscando categoría con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        CategoriaMenu c = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada con id: {}", id);
                    return new RuntimeException("No existe una categoría con el id indicado");
                });

        return new CategoriaMenuResponseDTO(
                c.getId(),
                c.getNombre(),
                c.getEstado()
        );
    }

    @Override
    public CategoriaMenuResponseDTO cambiarEstado(Long id, Boolean estado) {
        log.info("Cambiando estado de categoría {} a {}", id, estado);

        CategoriaMenu c = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada para cambiar estado: {}", id);
                    return new RuntimeException("Categoría no encontrada");
                });

        // Actualiza solo el estado — no modifica nombre ni otros campos
        c.setEstado(estado);
        repository.save(c);
        log.info("Estado de categoría {} actualizado a {}", id, estado);

        return new CategoriaMenuResponseDTO(
                c.getId(),
                c.getNombre(),
                c.getEstado()
        );
    }

    @Override
    public List<CategoriaMenuResponseDTO> listarPorEstado(Boolean estado) {
        log.info("Listando categorías con estado: {}", estado);

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<CategoriaMenuResponseDTO> lista = new ArrayList<>();
        List<CategoriaMenu> categorias = repository.findByEstado(estado);

        for (CategoriaMenu categoria : categorias) {
            CategoriaMenuResponseDTO dto = new CategoriaMenuResponseDTO(
                    categoria.getId(),
                    categoria.getNombre(),
                    categoria.getEstado()
            );
            lista.add(dto);
        }

        log.info("Total categorías con estado {}: {}", estado, lista.size());
        return lista;
    }
}