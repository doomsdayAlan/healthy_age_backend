package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {
    @PropertyName("id_notificacion")
    @JsonProperty("id_notificacion")
    private String idNotificacion;

    @PropertyName("id_tratamiento")
    @JsonProperty("id_tratamiento")
    @NotBlank(message = "El id de tratamiento es requerido")
    private String idTratamiento;

    @NotBlank(message = "La fecha es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    private String fecha;

    @PropertyName("tipo_notificacion")
    @JsonProperty("tipo_notificacion")
    @NotBlank(message = "El tipo de notificación es requerido")
    private String tipoNotificacion;

    @PropertyName("id_notificacion")
    public String getIdNotificacion() {
        return idNotificacion;
    }

    @PropertyName("id_notificacion")
    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    @PropertyName("id_tratamiento")
    public String getIdTratamiento() {
        return idTratamiento;
    }

    @PropertyName("id_tratamiento")
    public void setIdTratamiento(String idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @PropertyName("tipo_notificacion")
    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    @PropertyName("tipo_notificacion")
    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }
}
