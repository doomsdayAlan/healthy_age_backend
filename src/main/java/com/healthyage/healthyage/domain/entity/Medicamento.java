package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Medicamento {
    @PropertyName("id_medicamento")
    @JsonProperty("id_medicamento")
    @Schema(description = "Id del medicamento", example = "K1r2bz38FEgRTudHUMHx")
    private String idMedicamento;
    
    @PropertyName("nombre_medicamento")
    @JsonProperty("nombre_medicamento")
    @NotBlank(message = "El nombre del medicamento es requerido")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$", message = "El nombre del medicamento solo debe contener letras válidas")
    @Schema(description = "Nombre del medicamento", example = "K1r2bz38FEgRTudHUMHx")
    private String nombreMedicamento;

    @PropertyName("id_grupo_terapeutico")
    @JsonProperty("id_grupo_terapeutico")
    @Schema(description = "Id del grupo terapeutico al que pertenece el medicamento", example = "K1r2bz38FEgRTudHUMHx")
    @Default
    @Deprecated(forRemoval = true, since = "1.0.2")
    /**
     * @deprecated
     */
    private String idGrupoTerapeutico = "";

    @PropertyName("id_medicamento")
    public String getIdMedicamento() {
        return idMedicamento;
    }

    @PropertyName("id_medicamento")
    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    @PropertyName("nombre_medicamento")
    @JsonProperty("nombre_medicamento")
    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    @PropertyName("nombre_medicamento")
    @JsonProperty("nombre_medicamento")
    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    @PropertyName("id_grupo_terapeutico")
    @Deprecated(forRemoval = true, since = "1.0.2")
    /**
     * @deprecated
     */
    public String getIdGrupoTerapeutico() {
        return idGrupoTerapeutico;
    }

    @PropertyName("id_grupo_terapeutico")
    @Deprecated(forRemoval = true, since = "1.0.2")
    /**
     * @deprecated
     */
    public void setIdGrupoTerapeutico(String idGrupoTerapeutico) {
        this.idGrupoTerapeutico = idGrupoTerapeutico;
    }
}
