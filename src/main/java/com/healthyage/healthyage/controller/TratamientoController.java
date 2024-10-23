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

import com.healthyage.healthyage.domain.Tratamiento;
import com.healthyage.healthyage.service.TratamientoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tratamientos")
public class TratamientoController {
    private final TratamientoService servicio;

    @GetMapping("")
    public ResponseEntity<List<Tratamiento>> obtenerTratamientos() throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerTratamientos(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Tratamiento> guardarTratamiento(@RequestBody Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.guardarTratamiento(tratamiento), HttpStatus.OK);
    }

    @GetMapping("/{id-tratamiento}")
    public ResponseEntity<Tratamiento> obtenerTratamiento(@PathVariable("id-tratamiento") String idTratamiento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerTratamiento(idTratamiento), HttpStatus.OK);
    }

    @PutMapping("/{id-tratamiento}")
    public ResponseEntity<Tratamiento> actualizarTratamiento(@PathVariable("id-tratamiento") String idTratamiento,
            @RequestBody Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.actualizarTratamiento(idTratamiento, tratamiento), HttpStatus.OK);
    }

    @DeleteMapping("/{id-tratamiento}")
    public ResponseEntity<String> borrarTratamiento(@PathVariable("id-tratamiento") String idTratamiento)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.borrarTratamiento(idTratamiento), HttpStatus.OK);
    }
}
