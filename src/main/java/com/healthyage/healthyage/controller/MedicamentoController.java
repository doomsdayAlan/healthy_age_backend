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

import com.healthyage.healthyage.domain.entity.Medicamento;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.MedicamentoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/medicamentos")
public class MedicamentoController {
    private final MedicamentoService servicio;

    @GetMapping("")
    public ResponseEntity<List<Medicamento>> obtenerMedicamentos() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerMedicamentos());
    }

    @PostMapping("")
    public ResponseEntity<Medicamento> guardarMedicamento(@RequestBody @Valid Medicamento medicamento)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.guardarMedicamento(medicamento);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else 
            throw ResourceNotFoundException.createWith("medicamento");
    }

    @GetMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> obtenerMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerMedicamento(idMedicamento));
    }

    @PutMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> actualizarMedicamento(@PathVariable("id-medicamento") String idMedicamento,
            @RequestBody @Valid Medicamento medicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.actualizarMedicamento(idMedicamento, medicamento));
    }

    @DeleteMapping("/{id-medicamento}")
    public ResponseEntity<String> borrarMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.borrarMedicamento(idMedicamento));
    }
}
