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
public class Tratamiento {
    @PropertyName("id_tratamiento")
    @JsonProperty("id_tratamiento")
    private String idTratamiento;

    @PropertyName("id_usuario")
    @JsonProperty("id_usuario")
    private String idUsuario;
    
    @PropertyName("id_medicamento")
    @JsonProperty("id_medicamento")
    private String idMedicamento;
    
    @PropertyName("nom_tratamiento")
    @JsonProperty("nom_tratamiento")
    private String nomTratamiento;
    
    private String medico;
    
    @PropertyName("fecha_inicio")
    @JsonProperty("fecha_inicio")
    private String fechaInicio;
    
    @PropertyName("fecha_fin")
    @JsonProperty("fecha_fin")
    private String fechaFin;

    @PropertyName("id_tratamiento")
    public String getIdTratamiento() {
        return idTratamiento;
    }

    @PropertyName("id_tratamiento")
    public void setIdTratamiento(String idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    @PropertyName("id_usuario")
    public String getIdUsuario() {
        return idUsuario;
    }

    @PropertyName("id_usuario")
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @PropertyName("id_medicamento")
    public String getIdMedicamento() {
        return idMedicamento;
    }

    @PropertyName("id_medicamento")
    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    @PropertyName("nom_tratamiento")
    public String getNomTratamiento() {
        return nomTratamiento;
    }

    @PropertyName("nom_tratamiento")
    public void setNomTratamiento(String nomTratamiento) {
        this.nomTratamiento = nomTratamiento;
    }

    @PropertyName("fecha_inicio")
    public String getFechaInicio() {
        return fechaInicio;
    }

    @PropertyName("fecha_inicio")
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @PropertyName("fecha_fin")
    public String getFechaFin() {
        return fechaFin;
    }

    @PropertyName("fecha_fin")
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    } 
}
