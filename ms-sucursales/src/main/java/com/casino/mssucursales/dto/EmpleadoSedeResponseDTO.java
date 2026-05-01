package com.casino.mssucursales.dto;

import com.casino.mssucursales.model.EmpleadoSede;
import com.casino.mssucursales.model.EmpleadoSedeId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoSedeResponseDTO {

    private Long idEmpleado;

    private Long idSedeCasino;

}
