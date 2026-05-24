package com.casino.msmenu.service;

import com.casino.msmenu.dto.TipoPlatoRequestDTO;
import com.casino.msmenu.dto.TipoPlatoResponseDTO;
import com.casino.msmenu.model.TipoPlato;
import com.casino.msmenu.repository.TipoPlatoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

// Implementación del Service para la entidad TipoPlato
// Contiene toda la lógica de negocio relacionada a tipos de plato
// @Slf4j genera automáticamente el logger mediante Lombok
// @Service marca esta clase como componente de la capa de servicio
// @RequiredArgsConstructor genera constructor con los campos final
@Slf4j
@Service
@RequiredArgsConstructor
public class TipoPlatoServiceImpl implements TipoPlatoService {

    // Repositorio JPA inyectado mediante constructor
    private final TipoPlatoRepository tipoPlatoRepository;

    @Override
    public TipoPlatoResponseDTO crear(TipoPlatoRequestDTO dto) {
        log.info("Creando tipo de plato: {}", dto.getNombreTipoPlato());

        // Validación de negocio: nombre único en el sistema
        // Evita duplicar tipos como "Plato de Fondo", "Entrada", "Postre"
        if (tipoPlatoRepository.existsByNombreTipoPlato(dto.getNombreTipoPlato())) {
            log.warn("Tipo de plato ya existe: {}", dto.getNombreTipoPlato());
            throw new RuntimeException("El tipo de plato ya existe");
        }

        // Construcción de la entidad desde el DTO de entrada
        // null como id porque la base de datos lo genera automáticamente
        TipoPlato tipoPlato = new TipoPlato(
                null,
                dto.getNombreTipoPlato()
        );

        // Persistencia en base de datos mediante JpaRepository
        TipoPlato guardado = tipoPlatoRepository.save(tipoPlato);
        log.info("Tipo de plato creado con id: {}", guardado.getIdTipoPlato());

        // Retorna DTO en vez de entidad para no exponer estructura interna
        return new TipoPlatoResponseDTO(
                guardado.getIdTipoPlato(),
                guardado.getNombreTipoPlato()
        );
    }

    @Override
    public List<TipoPlatoResponseDTO> listar() {
        log.info("Listando todos los tipos de plato");

        // Bucle for tradicional para convertir lista de entidades a lista de DTOs
        List<TipoPlatoResponseDTO> lista = new ArrayList<>();
        List<TipoPlato> tipoPlatos = tipoPlatoRepository.findAll();

        for (TipoPlato tipoPlato : tipoPlatos) {
            lista.add(new TipoPlatoResponseDTO(
                    tipoPlato.getIdTipoPlato(),
                    tipoPlato.getNombreTipoPlato()
            ));
        }

        log.info("Total tipos de plato encontrados: {}", lista.size());
        return lista;
    }

    @Override
    public TipoPlatoResponseDTO obtenerPorId(Long id) {
        log.info("Buscando tipo de plato con id: {}", id);

        // orElseThrow lanza RuntimeException si no existe
        // GlobalExceptionHandler la captura y retorna 400
        TipoPlato t = tipoPlatoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("TipoPlato no encontrado con id: {}", id);
                    return new RuntimeException("TipoPlato no encontrado");
                });

        return new TipoPlatoResponseDTO(
                t.getIdTipoPlato(),
                t.getNombreTipoPlato()
        );
    }
}