package com.casino.mspedidos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;


    @Column(nullable = false)
    private Long usuarioId;


    @Column(nullable = false)
    private Long sedeId;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(nullable = false)
    private Double totalPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles;
}
