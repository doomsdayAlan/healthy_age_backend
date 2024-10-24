package com.healthyage.healthyage.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthyage.healthyage.domain.entity.GrupoTerapeutico;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.GrupoTerapeuticoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/grupos-terapeuticos")
public class GrupoTerapeuticoController {
    private final GrupoTerapeuticoService servicio;

    @GetMapping("")
    public ResponseEntity<List<GrupoTerapeutico>> obtenerGruposTerapeuticos() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerGruposTerapeuticos());
    }

    @PostMapping("")
    public ResponseEntity<GrupoTerapeutico> guardarGrupoTerapeutico(@RequestBody @Valid GrupoTerapeutico grupoTerapeutico)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.guardGrupoTerapeutico(grupoTerapeutico));
    }

    @GetMapping("/{id-grupo-terapeutico}")
    public ResponseEntity<GrupoTerapeutico> obtenGrupoTerapeutico(@PathVariable("id-grupo-terapeutico") String idGrupoTerapeutico)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.obtenGrupoTerapeutico(idGrupoTerapeutico);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else 
            throw ResourceNotFoundException.createWith("grupo terapeutico");
    }

    @PutMapping("/{id-grupo-terapeutico}")
    public ResponseEntity<GrupoTerapeutico> actualizarGrupoTerapeutico(@PathVariable("id-grupo-terapeutico") String idGrupoTerapeutico,
            @RequestBody @Valid GrupoTerapeutico grupoTerapeutico)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.actualizarGrupoTerapeutico(idGrupoTerapeutico, grupoTerapeutico));
    }

    @DeleteMapping("/{id-grupo-terapeutico}")
    public ResponseEntity<String> borrarGrupoTerapeutico(@PathVariable("id-grupo-terapeutico") String idGrupoTerapeutico)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.borrarGrupoTerapeutico(idGrupoTerapeutico));
    }
}
