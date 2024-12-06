package com.healthyage.healthyage.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUtilDTO {
    @NotBlank(message = "La dirección de email es requerida")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo electrónico no es válido.")
    @Schema(description = "Correo electronico del usuario", example = "correo@ejemplo.com")
    private String correo;

    @JsonProperty("pin_seguridad")
    @Pattern(regexp = "^[\\d]+$", message = "El pin de seguridad no es válido.")
    @Schema(description = "Pin de seguridad del usuario", example = "12345")
    @Size(min = 5, message = "El pin debe tener al menos 5 digitos")
    private String pinSeguridad;

    @JsonProperty("codigo_confirmacion")
    @Schema(description = "Codigo de confirmación enviado al mail del usuario", example = "S4bX87")
    private String codigoConfirmacion;
}
