package com.healthyage.healthyage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @PropertyName("id_usuario")
    @JsonProperty("id_usuario")
    private String idUsuario;
    private String nombre;
    private String correo;
    private String numero;

    @PropertyName("fecha_nacimiento")
    @JsonProperty("fecha_nacimiento")
    private String fechaNacimiento;

    private int rol;

    @PropertyName("clave_api")
    @JsonProperty("clave_api")
    private String claveApi;

    private String adulto;
    private String cuidador;

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
}
