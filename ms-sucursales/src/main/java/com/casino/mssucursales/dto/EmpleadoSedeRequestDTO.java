package com.casino.mssucursales.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSedeRequestDTO {

    @NotNull
    private Long idEmpleado;

    @NotNull
    private Long idSedeCasino;
}
