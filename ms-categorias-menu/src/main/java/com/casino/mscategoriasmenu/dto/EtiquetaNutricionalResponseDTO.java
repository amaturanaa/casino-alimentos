package com.casino.mscategoriasmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtiquetaNutricionalResponseDTO {

    private Long id;
    private Long categoriaId;
    private String nombreCategoria;
    private Integer calorias;
    private Double proteinas;
    private Double carbohidratos;
    private Double grasas;
    private Boolean esVegetariano;
    private Boolean esVegano;
    private Boolean contieneGluten;
}
