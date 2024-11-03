package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;

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
    private String idUsuario;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "La dirección de corree es requerida")
    private String correo;
    @NotBlank(message = "El número de teléfono es requerido")
    private String numero;

    @PropertyName("fecha_nacimiento")
    @JsonProperty("fecha_nacimiento")
    @NotBlank(message = "La fecha de nacimiento es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    private String fechaNacimiento;

    @Default
    private int rol = 0;

    @PropertyName("token_usuario")
    @JsonProperty("token_usuario")
    private String tokenUsuario;

    @PropertyName("clave_api")
    @JsonProperty("clave_api")
    @Default
    private String claveApi = "";
    
    @Default
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
