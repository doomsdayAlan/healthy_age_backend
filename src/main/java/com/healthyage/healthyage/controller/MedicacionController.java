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

import com.healthyage.healthyage.domain.entity.Medicacion;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.MedicacionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/medicaciones")
@Tag(name = "Api de medicaciones")
public class MedicacionController {
    private final MedicacionService servicio;

    @GetMapping("")
    public ResponseEntity<List<Medicacion>> obtenerMedicaciones() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerMedicaciones());
    }

    @PostMapping("")
    public ResponseEntity<Medicacion> guardarMedicacion(@RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.guardarMedicacion(medicacion);

        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La medicacion no existe");
    }

    @PostMapping("/tratamiento/{id-tratamiento}")
    public ResponseEntity<List<String>> guardarMedicacionPorTratamiento(
            @PathVariable("id-tratamiento") String idTratamiento, @RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var response = servicio.guardarMedicacionPorTratamiento(idTratamiento, medicacion);

        if (!response.isEmpty())
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo guardar la medicación");
    }

    @GetMapping("/{id-medicacion}")
    public ResponseEntity<Medicacion> obtenerMedicacion(@PathVariable("id-medicacion") String idMedicacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerMedicacion(idMedicacion));
    }

    @PutMapping("/{id-medicacion}")
    public ResponseEntity<Medicacion> actualizarMedicacion(@PathVariable("id-medicacion") String idMedicacion,
            @RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.actualizarMedicacion(idMedicacion, medicacion));
    }

    @DeleteMapping("/{id-medicacion}")
    public ResponseEntity<String> borrarMedicacion(@PathVariable("id-medicacion") String idMedicacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.borrarMedicacion(idMedicacion));
    }
}
