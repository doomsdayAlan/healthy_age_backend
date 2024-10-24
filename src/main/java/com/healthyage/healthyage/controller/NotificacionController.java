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

import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.NotificacionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/notificaciones")
public class NotificacionController {
    private final NotificacionService servicio;

    @GetMapping("")
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerNotificaciones());
    }

    @PostMapping("")
    public ResponseEntity<Notificacion> guardarNotificacion(@RequestBody @Valid Notificacion notificacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.guardarNotificacion(notificacion));
    }

    @GetMapping("/{id-medicamento}")
    public ResponseEntity<Notificacion> obtenerNotificacion(@PathVariable("id-medicamento") String idNotificacion)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.obtenerNotificacion(idNotificacion);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else 
            throw ResourceNotFoundException.createWith("medicamento");
    }

    @PutMapping("/{id-medicamento}")
    public ResponseEntity<Notificacion> actualizarNotificacion(@PathVariable("id-medicamento") String idNotificacion,
            @RequestBody @Valid Notificacion notificacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.actualizarNotificacion(idNotificacion, notificacion));
    }

    @DeleteMapping("/{id-medicamento}")
    public ResponseEntity<String> borrarNotificacion(@PathVariable("id-medicamento") String idNotificacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.borrarNotificacion(idNotificacion));
    }
}
