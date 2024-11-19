package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class Tratamiento {
    public static final String PATH = "tratamiento";

    @JsonProperty(value = "id_tratamiento", access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Id del tratamiento", example = "K1r2bz38FEgRTudHUMHx")
    private String idTratamiento;

    @JsonProperty("nombre_tratamiento")
    @NotBlank(message = "El nombre del tratamiento es requerido")
    @Schema(description = "Nombre dado al tratamiento", example = "Hipertensión")
    private String nombreTratamiento;

    @JsonProperty("id_usuario")
    @NotBlank(message = "El id de usuario es requerido")
    @Schema(description = "Id del usuario al que se le asigna el tratamiento", example = "K1r2bz38FEgRTudHUMHx")
    private String idUsuario;
    
    @JsonProperty("id_medicaciones")
    @Schema(description = "Arreglo de ids de las medicaciones del tratamiento en formato json", example = "[\"K1r2bz38FEgRTudHUMHx\"]")
    private String idMedicaciones;

    @NotBlank(message = "El medico es requerido")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El nombre del médico solo debe contener letras válidas")
    @Schema(description = "Nombre del médico que preescribe el tratamiento", example = "K1r2bz38FEgRTudHUMHx")
    private String medico;

    @JsonProperty("fecha_inicio")
    @NotBlank(message = "La fecha de inicio es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    @Schema(description = "Fecha en la que inicia el tratamiento", example = "03-11-2024")
    private String fechaInicio;

    @JsonProperty("fecha_fin")
    @NotBlank(message = "La fecha de fin es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    @Schema(description = "Fecha en la que finaliza el tratamiento", example = "03-11-2024")
    private String fechaFin;
}
