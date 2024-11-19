package com.healthyage.healthyage.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthyage.healthyage.domain.entity.Medicacion;
import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.domain.enumeration.TipoNotificacion;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.MedicacionService;
import com.healthyage.healthyage.service.NotificacionService;
import com.healthyage.healthyage.service.TratamientoService;
import com.healthyage.healthyage.service.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/medicaciones")
@Tag(name = "Api de medicaciones")
public class MedicacionController {
    private final MedicacionService medicacionService;
    private final TratamientoService tratamientoService;
    private final NotificacionService notificacionService;
    private final UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<List<Medicacion>> obtenerMedicaciones() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicacionService.obtenerMedicaciones());
    }

    @GetMapping("/usuario/{id-usuario}")
    public ResponseEntity<List<Medicacion>> obtenerMedicacionesPorUsuario(@PathVariable("id-usuario") String idUsuario,
            @RequestParam(required = false) LocalDate fecha)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicacionService.obtenerMedicacionesporUsuario(idUsuario, fecha));
    }

    @PostMapping("")
    public ResponseEntity<Medicacion> guardarMedicacion(@RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = medicacionService.guardarMedicacion(medicacion);

        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La medicacion no existe");
    }

    @PostMapping("/tratamiento/{id-tratamiento}")
    public ResponseEntity<Medicacion> guardarMedicacionPorTratamiento(
            @PathVariable("id-tratamiento") String idTratamiento, @RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var response = medicacionService.guardarMedicacion(medicacion);
        var tratamiento = tratamientoService.obtenerTratamiento(idTratamiento);
        var usuario = usuarioService.obtenerUsuario(tratamiento.getIdUsuario());
        var gson = new Gson();
        var medicaciones = new ArrayList<String>(
                gson.fromJson(tratamiento.getIdMedicaciones(), new TypeToken<List<String>>() {
                }.getType()));
        
        medicaciones.add(medicacion.getIdMedicacion());
        tratamiento.setIdMedicaciones(gson.toJson(medicaciones));
        tratamientoService.actualizarTratamiento(idTratamiento, tratamiento);

        var notificacionMedicacion = Notificacion.builder()
                .idTratamiento(idTratamiento)
                .idMedicacion(medicacion.getIdMedicacion())
                .idCuidador(usuario.getIdCuidador())
                .intervalo(medicacion.getIntervalo())
                .tipoIntervalo(medicacion.getTipoIntervalo())
                .marcaTiempo(NotificacionService.ajustarTiempoNotificacion(
                        LocalDateTime.parse(medicacion.getFechaInicio() + "T" + medicacion.getHoraInicio()),
                        medicacion.getIntervalo(), medicacion.getTipoIntervalo()))
                .tipoNotificacion(TipoNotificacion.RECORDATORIO_MEDICACION)
                .build();

        notificacionService.guardarNotificacion(notificacionMedicacion);

        if (medicacion.getRecordatorio() == 1) {
            var notificacionExistencia = Notificacion.builder()
                    .idTratamiento(idTratamiento)
                    .idMedicacion(medicacion.getIdMedicacion())
                    .idCuidador(usuario.getIdCuidador())
                    .marcaTiempo("9999-12-31T23:59:59")
                    .tipoNotificacion(TipoNotificacion.RECORDATORIO_EXISTENCIA)
                    .build();

            notificacionService.guardarNotificacion(notificacionExistencia);
        }

        if (!Objects.isNull(response))
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo guardar la medicación");
    }

    @GetMapping("/{id-medicacion}")
    public ResponseEntity<Medicacion> obtenerMedicacion(@PathVariable("id-medicacion") String idMedicacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicacionService.obtenerMedicacion(idMedicacion));
    }

    @PutMapping("/{id-medicacion}")
    public ResponseEntity<Medicacion> actualizarMedicacion(@PathVariable("id-medicacion") String idMedicacion,
            @RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicacionService.actualizarMedicacion(idMedicacion, medicacion));
    }

    @DeleteMapping("/{id-medicacion}")
    public ResponseEntity<String> borrarMedicacion(@PathVariable("id-medicacion") String idMedicacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicacionService.borrarMedicacion(idMedicacion));
    }
}
