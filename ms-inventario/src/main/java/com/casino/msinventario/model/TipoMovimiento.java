package com.casino.msinventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_movimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoMovimiento;

    @Column(nullable = false, unique = true, length = 50)
    private String nombreTipoMovimiento;
}
