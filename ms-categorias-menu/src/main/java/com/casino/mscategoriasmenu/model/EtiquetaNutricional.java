package com.casino.mscategoriasmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etiqueta_nutricional")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtiquetaNutricional {

    @OneToOne
    @JoinColumn(name = "categoria_id", nullable = false, unique = true)
    private CategoriaMenu categoriaMenu;

    @Column(nullable = false)
    private Integer calorias;

    @Column(nullable = false)
    private Double proteinas;

    @Column(nullable = false)
    private Double carbohidratos;

    @Column(nullable = false)
    private Double grasas;

    @Column(nullable = false)
    private Boolean esVegetariano = false;

    @Column(nullable = false)
    private Boolean esVegano = false;

    @Column(nullable = false)
    private Boolean contieneGluten = true;
}
