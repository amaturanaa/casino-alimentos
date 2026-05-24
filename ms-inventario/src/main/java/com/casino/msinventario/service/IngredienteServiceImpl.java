package com.casino.msinventario.service;

import com.casino.msinventario.client.SucursalesClient;
import com.casino.msinventario.dto.IngredienteRequestDTO;
import com.casino.msinventario.dto.IngredienteResponseDTO;
import com.casino.msinventario.dto.SedeCasinoResponseDTO;
import com.casino.msinventario.model.Ingrediente;
import com.casino.msinventario.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad Ingrediente
// Contiene toda la lógica de negocio relacionada al inventario de ingredientes
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class IngredienteServiceImpl implements IngredienteService {

    // Repositorio JPA para acceso a datos de Ingrediente
    private final IngredienteRepository ingredienteRepository;

    // Cliente Feign para comunicación síncrona con ms-sucursales
    // Verifica que la sede exista y esté operativa antes de crear el ingrediente
    private final SucursalesClient sucursalesClient;

    @Override
    public IngredienteResponseDTO crear(IngredienteRequestDTO dto) {
        log.info("Creando ingrediente: {} para sede: {}",
                dto.getNombreIngrediente(), dto.getSedeId());

        // Comunicación Feign con ms-sucursales para verificar sede
        // try/catch diferenciado: RuntimeException relanza limpio, Exception captura errores de red
        try {
            SedeCasinoResponseDTO sede = sucursalesClient.obtenerSedePorId(dto.getSedeId());
            if (!sede.getEstadoOperativo()) {
                log.warn("Sede no operativa para crear ingrediente: {}", dto.getSedeId());
                throw new RuntimeException("La sede " + sede.getNombreSede()
                        + " no está operativa");
            }
            log.info("Sede verificada: {}", sede.getNombreSede());
        } catch (RuntimeException e) {
            // Si el mensaje empieza con "La sede" es un error de negocio — se relanza sin modificar
            if (e.getMessage() != null && e.getMessage().startsWith("La sede")) {
                throw e;
            }
            log.error("Error al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede con id: "
                    + dto.getSedeId());
        } catch (Exception e) {
            log.error("Error inesperado al verificar sede: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la sede: " + e.getMessage());
        }

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        Ingrediente ingrediente = new Ingrediente(
                null,
                dto.getNombreIngrediente(),
                dto.getSedeId(),
                dto.getUnidadMedida(),
                dto.getStockActual(),
                dto.getStockMinimo()
        );

        // Persistencia en base de datos mediante JpaRepository
        Ingrediente guardado = ingredienteRepository.save(ingrediente);
        log.info("Ingrediente creado con id: {}", guardado.getIdIngrediente());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return mapToDTO(guardado);
    }

    @Override
    public IngredienteResponseDTO obtenerPorId(Long id) {
        log.info("Buscando ingrediente con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ingrediente no encontrado con id: {}", id);
                    return new RuntimeException("Ingrediente no encontrado");
                });

        return mapToDTO(ingrediente);
    }

    @Override
    public List<IngredienteResponseDTO> listar() {
        log.info("Listando todos los ingredientes");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();

        for (Ingrediente ingrediente : ingredientes) {
            lista.add(mapToDTO(ingrediente));
        }

        log.info("Total ingredientes encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public List<IngredienteResponseDTO> listarPorSede(Long sedeId) {
        log.info("Listando ingredientes para sede: {}", sedeId);

        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findBySedeId(sedeId);

        for (Ingrediente ingrediente : ingredientes) {
            lista.add(mapToDTO(ingrediente));
        }

        log.info("Total ingredientes en sede {}: {}", sedeId, lista.size());
        return lista;
    }

    @Override
    public List<IngredienteResponseDTO> listarStockBajo() {
        log.info("Listando ingredientes con stock bajo");

        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();

        // Filtra manualmente los ingredientes con stock actual menor o igual al mínimo
        for (Ingrediente ingrediente : ingredientes) {
            if (ingrediente.getStockActual() <= ingrediente.getStockMinimo()) {
                lista.add(mapToDTO(ingrediente));
            }
        }

        log.info("Total ingredientes con stock bajo: {}", lista.size());
        return lista;
    }

    @Override
    public List<IngredienteResponseDTO> listarStockBajoPorSede(Long sedeId) {
        log.info("Listando ingredientes con stock bajo para sede: {}", sedeId);

        List<IngredienteResponseDTO> lista = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findBySedeId(sedeId);

        // Filtra los ingredientes de la sede con stock actual menor o igual al mínimo
        for (Ingrediente ingrediente : ingredientes) {
            if (ingrediente.getStockActual() <= ingrediente.getStockMinimo()) {
                lista.add(mapToDTO(ingrediente));
            }
        }

        log.info("Total ingredientes con stock bajo en sede {}: {}", sedeId, lista.size());
        return lista;
    }

    @Override
    public IngredienteResponseDTO actualizar(Long id, IngredienteRequestDTO dto) {
        log.info("Actualizando ingrediente con id: {}", id);

        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ingrediente no encontrado para actualizar: {}", id);
                    return new RuntimeException("Ingrediente no encontrado");
                });

        // Actualiza todos los campos del ingrediente desde el DTO
        ingrediente.setNombreIngrediente(dto.getNombreIngrediente());
        ingrediente.setSedeId(dto.getSedeId());
        ingrediente.setUnidadMedida(dto.getUnidadMedida());
        ingrediente.setStockActual(dto.getStockActual());
        ingrediente.setStockMinimo(dto.getStockMinimo());

        Ingrediente actualizado = ingredienteRepository.save(ingrediente);
        log.info("Ingrediente actualizado con id: {}", actualizado.getIdIngrediente());
        return mapToDTO(actualizado);
    }

    // Convierte entidad JPA a DTO de respuesta
    // Evita exponer campos internos de la base de datos al cliente
    // Calcula stockBajo comparando stockActual con stockMinimo
    private IngredienteResponseDTO mapToDTO(Ingrediente i) {
        return new IngredienteResponseDTO(
                i.getIdIngrediente(),
                i.getNombreIngrediente(),
                i.getSedeId(),
                i.getUnidadMedida(),
                i.getStockActual(),
                i.getStockMinimo(),
                i.getStockActual() <= i.getStockMinimo()
        );
    }
}