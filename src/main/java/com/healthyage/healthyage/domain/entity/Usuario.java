package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @PropertyName("id_usuario")
    @JsonProperty("id_usuario")
    @Schema(description = "Id del usuario", example = "K1r2bz38FEgRTudHUMHx")
    private String idUsuario;

    @NotBlank(message = "El nombre es requerido")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo debe contener letras válidas y espacios")
    @Schema(description = "Nombre del usuario", example = "Manuel Hernández")
    private String nombre;

    @NotBlank(message = "La dirección de corree es requerida")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "El correo electrónico no es válido.")
    @Schema(description = "Correo electronico del usuario", example = "correo@ejemplo.com")
    private String correo;
    
    @NotBlank(message = "El número de teléfono es requerido")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?(\\(?\\d{3}\\)?[- ]?)?\\d{3}[- ]?\\d{4}$", message = "El número de teléfono no es válido")
    @Schema(description = "Número de teléfono del usuario", example = "562-895-6547")
    private String numero;

    @PropertyName("fecha_nacimiento")
    @JsonProperty("fecha_nacimiento")
    @NotBlank(message = "La fecha de nacimiento es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    @Schema(description = "Fecha de nacimiento del usuario", example = "29-04-1954")
    private String fechaNacimiento;

    @Default
    @Schema(description = "Rol del usuario (Cuidador = 0, Adulto Mayor = 1)", example = "1")
    private int rol = 0;

    @PropertyName("token_usuario")
    @JsonProperty("token_usuario")
    @NotBlank(message = "El token de usuario es requerido")
    @Schema(description = "Token del usuario para enviar notificaciones", example = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjM2ZDg1YTgxY2Q4NGE4N2Y5ODczOTY4ZGFmYjQ4YzU4NDNkYTliNzEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzL")
    private String tokenUsuario;

    @PropertyName("clave_api")
    @JsonProperty("clave_api")
    @Default
    @Schema(description = "clave de api del inicios de sesión del usuario con redes sociales", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String claveApi = "";

    @Default
    @Schema(description = "Cuidador asignado al adulto mayor", example = "K1r2bz38FEgRTudHUMHx")
    private String cuidador = "";

    @PropertyName("id_usuario")
    public String getIdUsuario() {
        return idUsuario;
    }

    @PropertyName("id_usuario")
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @PropertyName("fecha_nacimiento")
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    @PropertyName("fecha_nacimiento")
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @PropertyName("clave_api")
    public String getClaveApi() {
        return claveApi;
    }

    @PropertyName("clave_api")
    public void setClaveApi(String claveApi) {
        this.claveApi = claveApi;
    }

    @PropertyName("token_usuario")
    public String getTokenUsuario() {
        return tokenUsuario;
    }

    @PropertyName("token_usuario")
    public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }
}
