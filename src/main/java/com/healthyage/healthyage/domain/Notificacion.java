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
public class Notificacion {
    @PropertyName("id_notificacion")
    @JsonProperty("id_notificacion")
    private String idNotificacion;

    @PropertyName("id_tratamiento")
    @JsonProperty("id_tratamiento")
    private String idTratamiento;

    private String fecha;

    @PropertyName("tipo_notificacion")
    @JsonProperty("tipo_notificacion")
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
