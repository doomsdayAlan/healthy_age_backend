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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.domain.enumeration.TipoNotificacion;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.NotificacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/notificaciones")
@Tag(name = "Api de notificaciones")
public class NotificacionController {
    private final NotificacionService notificacionService;
    
    @Operation(summary = "Obtener todas las notificaciones", description = """
            Retorna una colección con todas las notificaciones de la base de datos
            """)
    @GetMapping("")
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(notificacionService.obtenerNotificaciones());
    }

    @Operation(summary = "Guardar medicamento", description = """
            Retorna un objeto de tipo Notificación con los mismos datos ingresados además de incluir el id del objeto guardado en la base de datos
            """)
    @PostMapping("")
    public ResponseEntity<Notificacion> guardarNotificacion(@RequestBody @Valid Notificacion notificacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(notificacionService.guardarNotificacion(notificacion));
    }

    @Operation(summary = "Obtener notificación por id", description = """
            Retorna el objeto Notificación relacionado con el id dado del documento
            """)
    @GetMapping("/{id-notificacion}")
    public ResponseEntity<Notificacion> obtenerNotificacion(@PathVariable("id-notificacion") String idNotificacion)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = notificacionService.obtenerNotificacion(idNotificacion);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La notificación no existe");
    }

    @Operation(summary = "Actualizar notificación", description = """
            Retorna un objeto de tipo Notificación con los mismos datos ingresados y actualizados
            """)
    @PutMapping("/{id-notificacion}")
    public ResponseEntity<Notificacion> actualizarNotificacion(@PathVariable("id-notificacion") String idNotificacion,
            @RequestParam(defaultValue = "false") boolean pospuesto,
            @RequestParam(defaultValue = "false") boolean aceptado,
            @RequestBody @Valid Notificacion notificacion)
            throws InterruptedException, ExecutionException {
                
            if(pospuesto) {
                var notificacionAdultoMayor = notificacion; 
                notificacionAdultoMayor.setTipoNotificacion(TipoNotificacion.RECORDATORIO_ADULTO_MAYOR);
                notificacionService.guardarNotificacion(notificacionAdultoMayor);
            }    

        return ResponseEntity.ok(notificacionService.actualizarNotificacion(idNotificacion, notificacion, pospuesto, aceptado));
    }

    @Operation(summary = "Eliminar notificación", description = """
            Retorna un string con el timestamp del momento en el que el registro fue eliminado
            """)
    @DeleteMapping("/{id-notificacion}")
    public ResponseEntity<String> borrarNotificacion(@PathVariable("id-notificacion") String idNotificacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(notificacionService.borrarNotificacion(idNotificacion));
    }
}
