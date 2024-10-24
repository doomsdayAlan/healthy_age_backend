package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;

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
    @PropertyName("id_tratamiento")
    @JsonProperty("id_tratamiento")
    private String idTratamiento;

    @PropertyName("id_usuario")
    @JsonProperty("id_usuario")
    @NotBlank(message = "El id de usuario es requerido")
    private String idUsuario;
    
    @PropertyName("id_medicamento")
    @JsonProperty("id_medicamento")
    @NotBlank(message = "El id de medicamento es requerido")
    private String idMedicamento;
    
    @PropertyName("nom_tratamiento")
    @JsonProperty("nom_tratamiento")
    @NotBlank(message = "El nombre del tratamiento es requerido")
    private String nomTratamiento;
    
    @NotBlank(message = "El medico es requerido")
    private String medico;
    
    @PropertyName("fecha_inicio")
    @JsonProperty("fecha_inicio")
    @NotBlank(message = "La fecha de inicio es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    private String fechaInicio;
    
    @PropertyName("fecha_fin")
    @JsonProperty("fecha_fin")
    @NotBlank(message = "La fecha de fin es requerida")
    @Pattern(regexp = "^(0[1-9]|[12][\\d]|3[01])[\\/\\-](0[1-9]|1[0-2])[\\/\\-](19|20)\\d\\d$", message = "El formato de fecha es incorrecto (DD/MM/YYYY o DD-MM-YYYY)")
    private String fechaFin;

    @NotBlank(message = "La hora es requerida")
    @Pattern(regexp = "^([01][\\d]|2[0-3]):[0-5][\\d]$", message = "El formato de hora es incorrecto (HH:mm) (24 horas)")
    private String hora;

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
