package com.casino.msusuarios.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private Long id_usuario;
    private String rut_usuario;
    private String pnombre_usuario;
    private String snombre_usuario;
    private String appaterno_usuario;
    private String apmaterno_usuario;
    private String email;
    private Boolean activo;
    private String nombre_rol;
}
