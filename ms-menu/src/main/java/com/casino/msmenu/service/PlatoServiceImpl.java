package com.casino.msmenu.service;

import com.casino.msmenu.client.CategoriasMenuClient;
import com.casino.msmenu.dto.CategoriaMenuResponseDTO;
import com.casino.msmenu.dto.PlatoRequestDTO;
import com.casino.msmenu.dto.PlatoResponseDTO;
import com.casino.msmenu.model.Plato;
import com.casino.msmenu.model.TipoPlato;
import com.casino.msmenu.repository.PlatoRepository;
import com.casino.msmenu.repository.TipoPlatoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Plato
// Contiene toda la lógica de negocio relacionada a platos del menú
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class PlatoServiceImpl implements PlatoService {

    // Repositorio JPA para acceso a datos de Plato
    private final PlatoRepository platoRepository;

    // Repositorio JPA para acceso a datos de TipoPlato
    // Necesario para verificar que el tipo de plato exista antes de crear
    private final TipoPlatoRepository tipoPlatoRepository;

    // Cliente Feign para comunicación síncrona con ms-categorias-menu
    // Verifica que la categoría exista y esté activa antes de crear el plato
    private final CategoriasMenuClient categoriasMenuClient;

    @Override
    public PlatoResponseDTO crear(PlatoRequestDTO dto) {
        log.info("Creando plato: {}", dto.getNombrePlato());

        // Verifica que el tipo de plato exista en la base de datos local
        TipoPlato tipoPlato = tipoPlatoRepository.findById(dto.getTipoPlatoId())
                .orElseThrow(() -> {
                    log.error("TipoPlato no encontrado: {}", dto.getTipoPlatoId());
                    return new RuntimeException("TipoPlato no encontrado");
                });

        // Comunicación Feign con ms-categorias-menu para verificar categoría
        // try/catch diferenciado: RuntimeException relanza limpio, Exception captura errores de red
        try {
            CategoriaMenuResponseDTO categoria = categoriasMenuClient
                    .obtenerCategoriaPorId(dto.getCategoriaId());
            if (!categoria.getEstado()) {
                log.warn("Categoría inactiva: {}", dto.getCategoriaId());
                throw new RuntimeException("La categoría no está activa");
            }
            log.info("Categoría verificada: {}", categoria.getNombre());
        } catch (RuntimeException e) {
            // Relanza el RuntimeException sin modificar el mensaje
            // Si es error de negocio (categoría inactiva) lo propaga limpio
            throw e;
        } catch (Exception e) {
            log.error("Error al verificar categoría en ms-categorias-menu: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la categoría: " + e.getMessage());
        }

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        // true como disponible porque un nuevo plato siempre está disponible
        Plato plato = new Plato(
                null,
                dto.getNombrePlato(),
                dto.getDescripcionPlato(),
                dto.getPrecioReferencial(),
                tipoPlato,
                dto.getCategoriaId(),
                true
        );

        // Persistencia en base de datos mediante JpaRepository
        Plato guardado = platoRepository.save(plato);
        log.info("Plato creado con id: {}", guardado.getIdPlato());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToDTO(guardado);
    }

    @Override
    public List<PlatoResponseDTO> listar() {
        log.info("Listando todos los platos");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findAll();

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        log.info("Total platos encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public PlatoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando plato con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Plato no encontrado con id: {}", id);
                    return new RuntimeException("Plato no encontrado");
                });

        return mapToDTO(plato);
    }

    @Override
    public PlatoResponseDTO cambiarDisponibilidad(Long id, Boolean disponible) {
        log.info("Cambiando disponibilidad de plato {} a {}", id, disponible);

        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Plato no encontrado para cambiar disponibilidad: {}", id);
                    return new RuntimeException("Plato no encontrado");
                });

        // Actualiza solo la disponibilidad — no modifica otros campos
        plato.setDisponible(disponible);
        Plato actualizado = platoRepository.save(plato);
        log.info("Disponibilidad de plato {} actualizada a {}", id, disponible);
        return mapToDTO(actualizado);
    }

    @Override
    public List<PlatoResponseDTO> listarPorTipo(Long tipoPlatoId) {
        log.info("Listando platos por tipo: {}", tipoPlatoId);

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findByTipoPlato_IdTipoPlato(tipoPlatoId);

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        log.info("Total platos de tipo {}: {}", tipoPlatoId, lista.size());
        return lista;
    }

    @Override
    public List<PlatoResponseDTO> listarPorCategoria(Long categoriaId) {
        log.info("Listando platos por categoría: {}", categoriaId);

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findByCategoriaId(categoriaId);

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        log.info("Total platos de categoría {}: {}", categoriaId, lista.size());
        return lista;
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Incluye el nombre del tipo de plato en vez del id para enriquecer la respuesta
    private PlatoResponseDTO mapToDTO(Plato p) {
        return new PlatoResponseDTO(
                p.getIdPlato(),
                p.getNombrePlato(),
                p.getDescripcionPlato(),
                p.getPrecioReferencial(),
                p.getTipoPlato().getNombreTipoPlato(),
                p.getCategoriaId(),
                p.getDisponible()
        );
    }
}