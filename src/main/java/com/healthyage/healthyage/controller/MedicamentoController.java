package com.healthyage.healthyage.controller;

import java.util.List;
import java.util.Objects;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/medicamentos")
@Tag(name = "Medicamentos")
public class MedicamentoController {
    private final MedicamentoService medicamentoService;

    @Operation(summary = "Obtener todos los medicamentos", description = """
            Retorna una colección con todos los medicamentos de la base de datos
            """)
    @GetMapping("")
    public ResponseEntity<List<Medicamento>> obtenerMedicamentos() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicamentoService.obtenerMedicamentos());
    }

    @Operation(summary = "Guardar medicamento", description = """
            Retorna un objeto de tipo Medicamento con los mismos datos ingresados además de incluir el id del objeto guardado en la base de datos
            """)
    @PostMapping("")
    public ResponseEntity<Medicamento> guardarMedicamento(@RequestBody @Valid Medicamento medicamento)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        return ResponseEntity.ok(medicamentoService.guardarMedicamento(medicamento));
    }

    @Operation(summary = "Obtener medicamento por id", description = """
            Retorna el objeto Medicamento relacionado con el id dado del documento
            """)
    @GetMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> obtenerMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        var response = medicamentoService.obtenerMedicamento(idMedicamento);

        if (Objects.nonNull(response))
            return ResponseEntity.ok(response);
        else
            throw new ResourceNotFoundException("No se encontró el medicamento");
    }

    @Operation(summary = "Actualizar medicamento", description = """
            Retorna un objeto de tipo Medicamento con los mismos datos ingresados y actualizados
            """)
    @PutMapping("/{id-medicamento}")
    public ResponseEntity<Medicamento> actualizarMedicamento(@PathVariable("id-medicamento") String idMedicamento,
            @RequestBody @Valid Medicamento medicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicamentoService.actualizarMedicamento(idMedicamento, medicamento));
    }

    @Operation(summary = "Eliminar medicamento", description = """
            Retorna un string con el timestamp del momento en el que el registro fue eliminado
            """)
    @DeleteMapping("/{id-medicamento}")
    public ResponseEntity<String> borrarMedicamento(@PathVariable("id-medicamento") String idMedicamento)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicamentoService.borrarMedicamento(idMedicamento));
    }
}
