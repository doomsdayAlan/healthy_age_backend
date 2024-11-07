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
public class Medicamento {
    @JsonProperty("id_medicamento")
    @Schema(description = "Id del medicamento", example = "K1r2bz38FEgRTudHUMHx")
    private String idMedicamento;
    
    @JsonProperty("nombre_medicamento")
    @NotBlank(message = "El nombre del medicamento es requerido")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El nombre del medicamento solo debe contener letras válidas")
    @Schema(description = "Nombre del medicamento", example = "K1r2bz38FEgRTudHUMHx")
    private String nombreMedicamento;
}
