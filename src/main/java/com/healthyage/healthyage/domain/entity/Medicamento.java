package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.firebase.database.annotations.Nullable;

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
    private String idMedicamento;
    
    @PropertyName("nombre_medicamento")
    @JsonProperty("nombre_medicamento")
    private String nombreMedicamento;

    @PropertyName("id_grupo_terapeutico")
    @JsonProperty("id_grupo_terapeutico")
    @NotBlank(message = "El id del grupo terapeutico es requerido")
    private String idGrupoTerapeutico;

    @NotBlank(message = "La frecuencia es requerida")
    private int frecuencia;

    @NotBlank(message = "El color es requerido")
    private String color;

    @PropertyName("unidad_medida")
    @JsonProperty("unidad_medida")
    @NotBlank(message = "La unidad de medida es requerida")
    private String unidadMedida;

    @PropertyName("via_administracion")
    @JsonProperty("via_administracion")
    @NotBlank(message = "La via de administración es requerida")
    private String viaAdministracion;
    
    @NotBlank(message = "La fecha es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    private String fecha;

    @Nullable
    private String notas;

    @PropertyName("recordar_existencia")
    @JsonProperty("recordar_existencia")
    @Default
    private int recordarExistencia = 0;

    @PropertyName("existencia_actual")
    @JsonProperty("existencia_actual")
    @Default
    private int existenciaActual = 0;   

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
    public String getIdGrupoTerapeutico() {
        return idGrupoTerapeutico;
    }

    @PropertyName("id_grupo_terapeutico")
    public void setIdGrupoTerapeutico(String idGrupoTerapeutico) {
        this.idGrupoTerapeutico = idGrupoTerapeutico;
    }

    @PropertyName("unidad_medida")
    public String getUnidadMedida() {
        return unidadMedida;
    }

    @PropertyName("unidad_medida")
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    @PropertyName("via_administracion")
    public String getViaAdministracion() {
        return viaAdministracion;
    }

    @PropertyName("via_administracion")
    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    @PropertyName("recordar_existencia")
    public int getRecordarExistencia() {
        return recordarExistencia;
    }

    @PropertyName("recordar_existencia")
    public void setRecordarExistencia(int recordarExistencia) {
        this.recordarExistencia = recordarExistencia;
    }

    @PropertyName("existencia_actual")
    public int getExistenciaActual() {
        return existenciaActual;
    }

    @PropertyName("existencia_actual")
    public void setExistenciaActual(int existenciaActual) {
        this.existenciaActual = existenciaActual;
    }
}
