package com.casino.msusuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(max = 12, message = "El RUT no puede superar 12 caracteres")
    private String rut_usuario;

    @NotBlank(message = "El primer nombre es obligatorio")
    @Size(max = 50)
    private String pnombre_usuario;

    @Size(max = 50)
    private String snombre_usuario;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 50)
    private String appaterno_usuario;

    @Size(max = 50)
    private String apmaterno_usuario;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Long rol_id;
}
