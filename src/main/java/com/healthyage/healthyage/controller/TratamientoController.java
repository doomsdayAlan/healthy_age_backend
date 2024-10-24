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

import com.healthyage.healthyage.domain.entity.Tratamiento;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.TratamientoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tratamientos")
public class TratamientoController {
    private final TratamientoService servicio;

    @GetMapping("")
    public ResponseEntity<List<Tratamiento>> obtenerTratamientos() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerTratamientos());
    }

    @PostMapping("")
    public ResponseEntity<Tratamiento> guardarTratamiento(@RequestBody @Valid Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.guardarTratamiento(tratamiento));
    }

    @GetMapping("/{id-tratamiento}")
    public ResponseEntity<Tratamiento> obtenerTratamiento(@PathVariable("id-tratamiento") String idTratamiento)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.obtenerTratamiento(idTratamiento);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else 
            throw ResourceNotFoundException.createWith("medicamento");
    }

    @PutMapping("/{id-tratamiento}")
    public ResponseEntity<Tratamiento> actualizarTratamiento(@PathVariable("id-tratamiento") String idTratamiento,
            @RequestBody @Valid Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.actualizarTratamiento(idTratamiento, tratamiento));
    }

    @DeleteMapping("/{id-tratamiento}")
    public ResponseEntity<String> borrarTratamiento(@PathVariable("id-tratamiento") String idTratamiento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.borrarTratamiento(idTratamiento));
    }
}
