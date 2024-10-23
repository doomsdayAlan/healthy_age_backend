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
}
