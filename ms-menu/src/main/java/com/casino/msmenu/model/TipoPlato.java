package com.casino.msmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_plato")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoPlato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoPlato;

    @Column(nullable = false, unique = true, length = 50)
    private String nombreTipoPlato;
}
