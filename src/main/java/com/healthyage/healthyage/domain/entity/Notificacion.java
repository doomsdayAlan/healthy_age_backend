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

    @NotBlank(message = "La marca de tiempo es requerida")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][\\d]|3[01])T(0[\\d]|1[\\d]|2[0-3]):([0-5][\\d]):([0-5][\\d]?)$", message = "El formato debe ser yyyy-MM-dd'T'HH:mm:ss")
    private String marcaTiempo;

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

    @PropertyName("marca_tiempo")
    public String getMarcaTiempo() {
        return marcaTiempo;
    }

    @PropertyName("marca_tiempo")
    public void setMarcaTiempo(String marcaTiempo) {
        this.marcaTiempo = marcaTiempo;
    }
}
