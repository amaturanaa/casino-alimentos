package com.casino.mscategoriasmenu.service;

import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalRequestDTO;
import com.casino.mscategoriasmenu.dto.EtiquetaNutricionalResponseDTO;
import com.casino.mscategoriasmenu.model.CategoriaMenu;
import com.casino.mscategoriasmenu.model.EtiquetaNutricional;
import com.casino.mscategoriasmenu.repository.CategoriaMenuRepository;
import com.casino.mscategoriasmenu.repository.EtiquetaNutricionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad EtiquetaNutricional
// Contiene toda la lógica de negocio relacionada a etiquetas nutricionales
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class EtiquetaNutricionalServiceImpl implements EtiquetaNutricionalService {

    // Repositorio JPA para acceso a datos de EtiquetaNutricional
    private final EtiquetaNutricionalRepository etiquetaRepository;

    // Repositorio JPA para acceso a datos de CategoriaMenu
    // Necesario para verificar que la categoría existe antes de crear la etiqueta
    private final CategoriaMenuRepository categoriaRepository;

    @Override
    public EtiquetaNutricionalResponseDTO crear(EtiquetaNutricionalRequestDTO dto) {
        log.info("Creando etiqueta nutricional para categoría: {}", dto.getCategoriaId());

        // Validación de negocio: cada categoría solo puede tener una etiqueta (1 a 1)
        if (etiquetaRepository.existsByCategoriaMenu_Id(dto.getCategoriaId())) {
            log.warn("Ya existe etiqueta para categoría: {}", dto.getCategoriaId());
            throw new RuntimeException("Ya existe etiqueta para esta categoría");
        }

        // Verifica que la categoría exista antes de asociar la etiqueta
        CategoriaMenu categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> {
                    log.error("Categoría no encontrada: {}", dto.getCategoriaId());
                    return new RuntimeException("Categoría no encontrada");
                });

        // Construcción de la entidad desde el DTO de entrada
        EtiquetaNutricional etiqueta = new EtiquetaNutricional();
        etiqueta.setCategoriaMenu(categoria);
        etiqueta.setCalorias(dto.getCalorias());
        etiqueta.setProteinas(dto.getProteinas());
        etiqueta.setCarbohidratos(dto.getCarbohidratos());
        etiqueta.setGrasas(dto.getGrasas());
        etiqueta.setEsVegetariano(dto.getEsVegetariano());
        etiqueta.setEsVegano(dto.getEsVegano());
        etiqueta.setContieneGluten(dto.getContieneGluten());

        // Persistencia en base de datos mediante JpaRepository
        EtiquetaNutricional guardada = etiquetaRepository.save(etiqueta);
        log.info("Etiqueta nutricional creada con id: {}", guardada.getIdEtiquetaNutricional());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToDTO(guardada);
    }

    @Override
    public EtiquetaNutricionalResponseDTO obtenerPorCategoria(Long categoriaId) {
        log.info("Buscando etiqueta para categoría: {}", categoriaId);

        // Busca la etiqueta navegando la relación OneToOne por categoriaMenu.id
        EtiquetaNutricional etiqueta = etiquetaRepository
                .findByCategoriaMenu_Id(categoriaId)
                .orElseThrow(() -> {
                    log.error("Etiqueta no encontrada para categoría: {}", categoriaId);
                    return new RuntimeException("Etiqueta no encontrada");
                });

        return mapToDTO(etiqueta);
    }

    @Override
    public EtiquetaNutricionalResponseDTO actualizar(Long id, EtiquetaNutricionalRequestDTO dto) {
        log.info("Actualizando etiqueta nutricional con id: {}", id);

        EtiquetaNutricional etiqueta = etiquetaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Etiqueta no encontrada para actualizar: {}", id);
                    return new RuntimeException("Etiqueta no encontrada");
                });

        // Actualiza todos los campos nutricionales — no se cambia la categoría asociada
        etiqueta.setCalorias(dto.getCalorias());
        etiqueta.setProteinas(dto.getProteinas());
        etiqueta.setCarbohidratos(dto.getCarbohidratos());
        etiqueta.setGrasas(dto.getGrasas());
        etiqueta.setEsVegetariano(dto.getEsVegetariano());
        etiqueta.setEsVegano(dto.getEsVegano());
        etiqueta.setContieneGluten(dto.getContieneGluten());

        EtiquetaNutricional actualizada = etiquetaRepository.save(etiqueta);
        log.info("Etiqueta nutricional actualizada con id: {}", actualizada.getIdEtiquetaNutricional());
        return mapToDTO(actualizada);
    }

    @Override
    public List<EtiquetaNutricionalResponseDTO> listar() {
        log.info("Listando todas las etiquetas nutricionales");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<EtiquetaNutricionalResponseDTO> lista = new ArrayList<>();
        List<EtiquetaNutricional> etiquetas = etiquetaRepository.findAll();

        for (EtiquetaNutricional etiqueta : etiquetas) {
            lista.add(mapToDTO(etiqueta));
        }

        log.info("Total etiquetas encontradas: {}", lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Incluye datos de la categoría asociada para enriquecer la respuesta
    private EtiquetaNutricionalResponseDTO mapToDTO(EtiquetaNutricional e) {
        return new EtiquetaNutricionalResponseDTO(
                e.getIdEtiquetaNutricional(),
                e.getCategoriaMenu().getId(),
                e.getCategoriaMenu().getNombre(),
                e.getCalorias(),
                e.getProteinas(),
                e.getCarbohidratos(),
                e.getGrasas(),
                e.getEsVegetariano(),
                e.getEsVegano(),
                e.getContieneGluten()
        );
    }
}