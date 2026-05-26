package com.casino.msmenu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

// Entidad JPA que representa la tabla "programacion_diaria" en db_menu
// @Entity indica que esta clase es una entidad persistente en la base de datos
// @Table define el nombre exacto de la tabla en la base de datos
// Define qué platos se sirven en cada sede por fecha con control de raciones
@Entity
@Table(name = "programacion_diaria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramacionDiaria {

    // Clave primaria de la tabla — generada automáticamente por la base de datos
    // @GeneratedValue con IDENTITY usa el autoincremento de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProgramacion;

    // Fecha de la programación en formato ISO: yyyy-MM-dd
    // LocalDate almacena solo la fecha sin hora
    // Ejemplo: 2026-05-15
    @Column(nullable = false)
    private LocalDate fecha;

    // Referencia a la sede en ms-sucursales donde se programa el menú
    // No es FK física — cada microservicio maneja su propia base de datos
    // La comunicación real se hace via Feign Client cuando es necesario
    @Column(nullable = false)
    private Long sedeId;

    // Relación ManyToOne con Plato dentro del mismo microservicio
    // Muchas programaciones pueden tener el mismo plato en distintas fechas o sedes
    // @JoinColumn define la columna FK en la tabla programacion_diaria
    // nullable = false indica que toda programación debe tener un plato asociado
    @ManyToOne
    @JoinColumn(name = "plato_id", nullable = false)
    private Plato plato;

    // Cantidad de raciones disponibles del plato para ese día y sede
    // Se descuenta automáticamente cuando un cliente realiza un pedido
    // Cuando llega a 0 el plato ya no está disponible para ese día
    @Column(nullable = false)
    private Integer racionesDisponibles;
}