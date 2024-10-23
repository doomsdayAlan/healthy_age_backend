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

import com.healthyage.healthyage.domain.Medicamento;
import com.healthyage.healthyage.service.MedicamentoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/medicamentos")
public class MedicamentoController {
    private final MedicamentoService servicio;

    @GetMapping("")
    public ResponseEntity<List<Medicamento>> obtenerMedicamentos() throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerMedicamentos(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Medicamento> guardarMedicamento(@RequestBody Medicamento medicamento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.guardarMedicamento(medicamento), HttpStatus.OK);
    }

    @GetMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> obtenerMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerMedicamento(idMedicamento), HttpStatus.OK);
    }

    @PutMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> actualizarMedicamento(@PathVariable("id-medicamento") String idMedicamento,
            @RequestBody Medicamento medicamento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.actualizarMedicamento(idMedicamento, medicamento), HttpStatus.OK);
    }

    @DeleteMapping("/{id-medicamento}")
    public ResponseEntity<String> borrarMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.borrarMedicamento(idMedicamento), HttpStatus.OK);
    }
}
