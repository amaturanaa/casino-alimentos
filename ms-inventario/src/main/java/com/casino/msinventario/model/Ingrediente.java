package com.casino.msinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingrediente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIngrediente;

    @Column(nullable = false, length = 100)
    private String nombreIngrediente;


    @Column(nullable = false)
    private Long sedeId;

    @Column(nullable = false, length = 20)
    private String unidadMedida;

    @Column(nullable = false)
    private Double stockActual;

    @Column(nullable = false)
    private Double stockMinimo;
}
