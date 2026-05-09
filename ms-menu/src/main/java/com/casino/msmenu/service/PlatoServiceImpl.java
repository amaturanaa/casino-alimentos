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
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository platoRepository;
    private final TipoPlatoRepository tipoPlatoRepository;
    private final CategoriasMenuClient categoriasMenuClient;

    private static final Logger log = LoggerFactory.getLogger(PlatoServiceImpl.class);


    @Override
    public PlatoResponseDTO crear(PlatoRequestDTO dto) {
        log.info("Creando plato: {}", dto.getNombrePlato());

        TipoPlato tipoPlato = tipoPlatoRepository.findById(dto.getTipoPlatoId())
                .orElseThrow(() -> {
                    log.error("TipoPlato no encontrado: {}", dto.getTipoPlatoId());
                    return new RuntimeException("TipoPlato no encontrado");
                });

        try {
            CategoriaMenuResponseDTO categoria = categoriasMenuClient.obtenerCategoriaPorId(dto.getCategoriaId());
            if (!categoria.getEstado()) {
                log.warn("Categoría inactiva: {}", dto.getCategoriaId());
                throw new RuntimeException("La categoría no está activa");
            }
            log.info("Categoría varificada: {}", categoria.getNombre());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al verificar categoría en ms-categorias-menu: {}", e.getMessage());
            throw new RuntimeException("No se pudo verificar la categoría: " + e.getMessage());
        }

        Plato plato = new Plato(
                null,
                dto.getNombrePlato(),
                dto.getDescripcionPlato(),
                dto.getPrecioReferencial(),
                tipoPlato,
                dto.getCategoriaId(),
                true
        );

        Plato guardado = platoRepository.save(plato);
        log.info("Plato creado con id: {}", guardado.getIdPlato());
        return mapToDTO(guardado);
    }

    @Override
    public List<PlatoResponseDTO> listar() {

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findAll();

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        return lista;
    }

    @Override
    public PlatoResponseDTO obtenerPorId(Long id) {

        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        return mapToDTO(plato);
    }

    @Override
    public PlatoResponseDTO cambiarDisponibilidad(Long id, Boolean disponible) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));
        plato.setDisponible(disponible);
        return mapToDTO(platoRepository.save(plato));
    }

    @Override
    public List<PlatoResponseDTO> listarPorTipo(Long tipoPlatoId) {

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findByTipoPlato_IdTipoPlato(tipoPlatoId);

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        return lista;
    }

    @Override
    public List<PlatoResponseDTO> listarPorCategoria(Long categoriaId) {

        List<PlatoResponseDTO> lista = new ArrayList<>();
        List<Plato> platos = platoRepository.findByCategoriaId(categoriaId);

        for (Plato plato : platos) {
            lista.add(mapToDTO(plato));
        }

        return lista;
    }

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
