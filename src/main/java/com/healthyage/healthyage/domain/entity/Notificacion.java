package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthyage.healthyage.domain.enumeration.TipoIntervalo;
import com.healthyage.healthyage.domain.enumeration.TipoNotificacion;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
public class Notificacion {
    public static final String PATH = "notificacion";

    @JsonProperty(value = "id_notificacion", access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Id de la notificación", example = "K1r2bz38FEgRTudHUMHx")
    private String idNotificacion;

    @JsonProperty("id_tratamiento")
    @Schema(description = "Id del tratamiento del recordatorio", example = "K1r2bz38FEgRTudHUMHx")
    private String idTratamiento;

    @JsonProperty("id_medicacion")
    @Schema(description = "Id de la medicacion del recordatorio", example = "K1r2bz38FEgRTudHUMHx")
    private String idMedicacion;

    @JsonProperty("id_cuidador")
    @Schema(description = "Id del cuidador al que se le enviará el recordatorio", example = "K1r2bz38FEgRTudHUMHx")
    private String idCuidador;

    @Default
    @Min(1)
    @Pattern(regexp = "^(?:[1-9]|1[\\d]|2[0-4])$", message = "La hora debe estar entre 1 y 24")
    @Schema(description = "Intervalo de tiempo entre tomas", example = "8")
    private int intervalo = 1;

    @JsonProperty("tipo_intervalo")
    @Default
    @Schema(description = "Intervalo de tiempo entre tomas", example = "HORAS")
    private TipoIntervalo tipoIntervalo = TipoIntervalo.MINUTOS;

    @JsonProperty(value = "marca_tiempo")
    @NotBlank(message = "La marca de tiempo es requerida")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][\\d]|3[01])T(0[\\d]|1[\\d]|2[0-3]):([0-5][\\d]):([0-5][\\d]?)$", message = "El formato debe ser yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Timestamp a la que está programada la notificación para enviarse", example = "2024-11-06T14:30:00")
    private String marcaTiempo;

    @JsonProperty("tipo_notificacion")
    @NotBlank(message = "El tipo de notificación es requerido")
    @Schema(description = "Tipo de notificacion que se envia", example = "RECORDATORIO/NOTIFICACION")
    private TipoNotificacion tipoNotificacion;
}
