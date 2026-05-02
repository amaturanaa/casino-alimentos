package com.casino.msmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Plato")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;

    @Column(nullable = false, length = 100)
    private String nombrePlato;

    @Column(length = 255)
    private String descripcionPlato;

    @Column(nullable = false)
    private Double precioReferencial;

    @ManyToOne
    @JoinColumn(name = "tipo_plato_id", nullable = false)
    private TipoPlato tipoPlato;

    // Referencia a ms-categorias-menu (solo Long, sin FK física)
    @Column(nullable = false)
    private Long categoriaId;

    @Column(nullable = false)
    private Boolean disponible = true;
}
