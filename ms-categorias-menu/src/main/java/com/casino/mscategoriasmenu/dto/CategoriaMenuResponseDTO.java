package com.casino.mscategoriasmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaMenuResponseDTO {

    private Long id;

    private String nombre;

    private Boolean estado;
}
