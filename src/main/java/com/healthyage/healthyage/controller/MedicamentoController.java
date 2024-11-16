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

import com.healthyage.healthyage.domain.entity.Medicamento;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.MedicamentoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/medicamentos")
@Tag(name = "Api de medicamentos")
public class MedicamentoController {
    private final MedicamentoService medicamentoService;

    @GetMapping("")
    public ResponseEntity<List<Medicamento>> obtenerMedicamentos() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicamentoService.obtenerMedicamentos());
    }

    @PostMapping("")
    public ResponseEntity<Medicamento> guardarMedicamento(@RequestBody @Valid Medicamento medicamento)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = medicamentoService.guardarMedicamento(medicamento);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El medicamento no existe");
    }

    @GetMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> obtenerMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicamentoService.obtenerMedicamento(idMedicamento));
    }

    @PutMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> actualizarMedicamento(@PathVariable("id-medicamento") String idMedicamento,
            @RequestBody @Valid Medicamento medicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicamentoService.actualizarMedicamento(idMedicamento, medicamento));
    }

    @DeleteMapping("/{id-medicamento}")
    public ResponseEntity<String> borrarMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicamentoService.borrarMedicamento(idMedicamento));
    }
}
