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
public class Medicamento {
    @PropertyName("id_medicamento")
    @JsonProperty("id_medicamento")
    private String idMedicamento;

    @PropertyName("id_grupo_terapeutico")
    @JsonProperty("id_grupo_terapeutico")
    private String idGrupoTerapeutico;
    private int frecuencia;
    private String color;
    private int dosis;

    @PropertyName("unidad_medida")
    @JsonProperty("unidad_medida")
    private String unidadMedida;

    @PropertyName("via_administracion")
    @JsonProperty("via_administracion")
    private String viaAdministracion;

    private String fecha;
    private String hora;
    private String notas;

    @PropertyName("recordar_existencia")
    @JsonProperty("recordar_existencia")
    private int recordarExistencia;

    @PropertyName("existencia_actual")
    @JsonProperty("existencia_actual")
    private int existenciaActual;

    @PropertyName("id_medicamento")
    public String getIdMedicamento() {
        return idMedicamento;
    }

    @PropertyName("id_medicamento")
    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
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
