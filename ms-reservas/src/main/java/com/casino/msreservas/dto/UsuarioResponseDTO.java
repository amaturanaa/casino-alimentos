package com.casino.msreservas.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private Long id_usuario;

    private String email;

    private Boolean activo;

}
