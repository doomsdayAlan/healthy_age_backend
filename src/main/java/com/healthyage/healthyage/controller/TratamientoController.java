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
import org.springframework.web.server.ResponseStatusException;

import com.healthyage.healthyage.domain.entity.Tratamiento;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.TratamientoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tratamientos")
@Tag(name = "Api de tratamientos")
public class TratamientoController {
    private final TratamientoService tratamientoService;

    @GetMapping("")
    public ResponseEntity<List<Tratamiento>> obtenerTratamientos() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(tratamientoService.obtenerTratamientos());
    }

    @PostMapping("")
    public ResponseEntity<Tratamiento> guardarTratamiento(@RequestBody @Valid Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(tratamientoService.guardarTratamiento(tratamiento));
    }

    @GetMapping("/{id-tratamiento}")
    public ResponseEntity<Tratamiento> obtenerTratamiento(@PathVariable("id-tratamiento") String idTratamiento)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = tratamientoService.obtenerTratamiento(idTratamiento);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El tratamiento no existe");
    }

    @GetMapping("/usuario/{id-usuario}")
    public ResponseEntity<List<Tratamiento>> obtenerTratamientosPorUsuario(
            @PathVariable("id-usuario") String idUsuario)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = tratamientoService.obtenerTratamientosPorUsuario(idUsuario);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id-tratamiento}")
    public ResponseEntity<Tratamiento> actualizarTratamiento(@PathVariable("id-tratamiento") String idTratamiento,
            @RequestBody @Valid Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(tratamientoService.actualizarTratamiento(idTratamiento, tratamiento));
    }

    @DeleteMapping("/{id-tratamiento}")
    public ResponseEntity<String> borrarTratamiento(@PathVariable("id-tratamiento") String idTratamiento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(tratamientoService.borrarTratamiento(idTratamiento));
    }
}
