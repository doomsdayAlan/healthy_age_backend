package com.healthyage.healthyage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.firestore.annotation.PropertyName;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrupoTerapeutico {
    @PropertyName("id_grupo_terapeutico")
    @JsonProperty("id_grupo_terapeutico")
    private String idGrupoTerapeutico;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    @PropertyName("nombre_grupo")
    @JsonProperty("nombre_grupo")
    @NotBlank(message = "El nombre del grupo es requerido")
    private String nombreGrupo;

    @PropertyName("id_grupo_terapeutico")
    public void setIdGrupoTerapeutico(String idGrupoTerapeutico) {
        this.idGrupoTerapeutico = idGrupoTerapeutico;
    }

    @PropertyName("id_grupo_terapeutico")
    public String getIdGrupoTerapeutico() {
        return idGrupoTerapeutico;
    }

    @PropertyName("nombre_grupo")
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    @PropertyName("nombre_grupo")
    public String getNombreGrupo() {
        return nombreGrupo;
    }
}
