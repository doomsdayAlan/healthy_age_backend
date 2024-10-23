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

import com.healthyage.healthyage.domain.Notificacion;
import com.healthyage.healthyage.service.NotificacionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/notificaciones")
public class NotificacionController {
    private final NotificacionService servicio;

    @GetMapping("")
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones() throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerNotificaciones(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Notificacion> guardarNotificacion(@RequestBody Notificacion notificacion)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.guardarNotificacion(notificacion), HttpStatus.OK);
    }

    @GetMapping("/{id-medicamento}")
    public ResponseEntity<Notificacion> obtenerNotificacion(@PathVariable("id-medicamento") String idNotificacion)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerNotificacion(idNotificacion), HttpStatus.OK);
    }

    @PutMapping("/{id-medicamento}")
    public ResponseEntity<Notificacion> actualizarNotificacion(@PathVariable("id-medicamento") String idNotificacion,
            @RequestBody Notificacion notificacion)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.actualizarNotificacion(idNotificacion, notificacion), HttpStatus.OK);
    }

    @DeleteMapping("/{id-medicamento}")
    public ResponseEntity<String> borrarNotificacion(@PathVariable("id-medicamento") String idNotificacion)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.borrarNotificacion(idNotificacion), HttpStatus.OK);
    }
}
