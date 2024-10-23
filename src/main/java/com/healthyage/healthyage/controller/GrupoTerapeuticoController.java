package com.healthyage.healthyage.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthyage.healthyage.domain.GrupoTerapeutico;
import com.healthyage.healthyage.service.GrupoTerapeuticoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/grupos-terapeuticos")
public class GrupoTerapeuticoController {
    private final GrupoTerapeuticoService servicio;

    @GetMapping("")
    public ResponseEntity<List<GrupoTerapeutico>> obtenerGruposTerapeuticos() throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerGruposTerapeuticos(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<GrupoTerapeutico> guardGrupoTerapeutico(@RequestBody GrupoTerapeutico grupoTerapeutico)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.guardGrupoTerapeutico(grupoTerapeutico), HttpStatus.OK);
    }

    @GetMapping("/{id-grupo-terapeutico}")
    public ResponseEntity<GrupoTerapeutico> obtenGrupoTerapeutico(@PathVariable("id-grupo-terapeutico") String idGrupoTerapeutico)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenGrupoTerapeutico(idGrupoTerapeutico), HttpStatus.OK);
    }

    @PutMapping("/{id-grupo-terapeutico}")
    public ResponseEntity<GrupoTerapeutico> actualizarGrupoTerapeutico(@PathVariable("id-grupo-terapeutico") String idGrupoTerapeutico,
            @RequestBody GrupoTerapeutico grupoTerapeutico)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.actualizarGrupoTerapeutico(idGrupoTerapeutico, grupoTerapeutico), HttpStatus.OK);
    }

    @DeleteMapping("/{id-grupo-terapeutico}")
    public ResponseEntity<String> borrarGrupoTerapeutico(@PathVariable("id-grupo-terapeutico") String idGrupoTerapeutico)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.borrarGrupoTerapeutico(idGrupoTerapeutico), HttpStatus.OK);
    }
}
