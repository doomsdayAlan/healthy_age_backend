package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.healthyage.healthyage.domain.enumeration.TipoIntervalo;
import com.healthyage.healthyage.domain.enumeration.Unindad;
import com.healthyage.healthyage.domain.enumeration.ViaAdministracion;

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
public class Medicacion {
    @JsonProperty(value = "id_medicacion", access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Id de la medicación", example = "K1r2bz38FEgRTudHUMHx")
    private String idMedicacion;

    @JsonProperty("id_medicamento")
    @Schema(description = "Id del medicamento", example = "K1r2bz38FEgRTudHUMHx")
    private String idMedicamento;

    @JsonProperty("fecha_inicio")
    @NotBlank(message = "La fecha de inicio es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    @Schema(description = "Fecha en la que inicia la medicacion", example = "03-11-2024")
    private String fechaInicio;

    @JsonProperty("hora_inicio")
    @NotBlank(message = "La hora de inicio del tratamiento es requerida")
    @Pattern(regexp = "^([01][\\d]|2[0-3]):[0-5][\\d]$", message = "El formato de hora es incorrecto (HH:mm) (24 horas)")
    @Schema(description = "Hora en la que inicia el tratamiento", example = "18:25")
    private String horaInicio;

    @JsonProperty("fecha_fin")
    @NotBlank(message = "La fecha de fin es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    @Schema(description = "Fecha en la que finaliza la medicacion", example = "03-11-2024")
    private String fechaFin;

    @Min(1)
    @Pattern(regexp = "^(?:[1-9]|1[\\d]|2[0-4])$", message = "La hora debe estar entre 1 y 24")
    @Schema(description = "Intervalo de tiempo entre tomas", example = "8")
    private int intervalo;

    @JsonProperty("tipo_intervalo")
    @NotBlank(message = "El tipo de intervalo es requerido")
    @Schema(description = "Intervalo de tiempo entre tomas", example = "HORAS")
    private TipoIntervalo tipoIntervalo;

    @NotBlank(message = "La dosis es requerida")
    @Schema(description = "Dosis del medicamento del tratamiento", example = "2")
    private int dosis;

    @JsonProperty("unidad")
    @NotBlank(message = "El tipo de dosis es requerido")
    @Schema(description = "Tipo de dosis del medicamento (mililitros, miligramos, unidades, etc)", example = "HORAS")
    private Unindad unidad;

    @JsonProperty("via_administración")
    @NotBlank(message = "La via de administración es requerida")
    @Schema(description = "La via de administración de la medicación", example = "HORAS")
    private ViaAdministracion viaAdministracion;

    @JsonProperty("existencia_actual")
    @NotBlank(message = "La existencia actual es requerida")
    @Schema(description = "Existencia actual del medicamento", example = "HORAS")
    private int existenciaActual;

    @Default
    @Schema(description = "Indicador de recordatorio cuando la existencia este por agotarse", example = "2")
    private int recordatorio = 0;

    @JsonProperty("limite_recordatorio")
    @Default
    @Schema(description = "La cantidad de unidades a la que se notifica cuando la existencia este por agotarse", example = "HORAS")
    private int limiteRecordatorio = 0;
}