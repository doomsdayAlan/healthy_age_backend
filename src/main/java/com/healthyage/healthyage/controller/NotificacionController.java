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

import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.NotificacionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/notificaciones")
@Tag(name = "Api de notificaciones")
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

    @GetMapping("/{id-notificacion}")
    public ResponseEntity<Notificacion> obtenerNotificacion(@PathVariable("id-notificacion") String idNotificacion)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.obtenerNotificacion(idNotificacion);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La notificación no existe");
    }

    @GetMapping("/tratamiento/{id-tratamiento}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorTratamiento(
            @PathVariable("id-tratamiento") String idTratamiento)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.obtenerNotificacionPorTratamiento(idTratamiento);

        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La notificación no existe");
    }

    @PutMapping("/{id-notificacion}")
    public ResponseEntity<Notificacion> actualizarNotificacion(@PathVariable("id-notificacion") String idNotificacion,
            @RequestBody @Valid Notificacion notificacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.actualizarNotificacion(idNotificacion, notificacion));
    }

    @DeleteMapping("/{id-notificacion}")
    public ResponseEntity<String> borrarNotificacion(@PathVariable("id-notificacion") String idNotificacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.borrarNotificacion(idNotificacion));
    }
}
