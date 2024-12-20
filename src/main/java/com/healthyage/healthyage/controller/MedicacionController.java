package com.healthyage.healthyage.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
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
import com.healthyage.healthyage.domain.entity.Medicacion;
import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.domain.enumeration.TipoNotificacion;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.MedicacionService;
import com.healthyage.healthyage.service.NotificacionService;
import com.healthyage.healthyage.service.TratamientoService;
import com.healthyage.healthyage.service.UsuarioService;
import com.healthyage.healthyage.util.ParseoSeguroUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/medicaciones")
@Tag(name = "Medicaciones")
public class MedicacionController {
    private final MedicacionService medicacionService;
    private final TratamientoService tratamientoService;
    private final NotificacionService notificacionService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Obtener todas las medicaciones", description = """
            Retorna una colección con todas las medicaciones de la base de datos
            """)
    @GetMapping("")
    public ResponseEntity<List<Medicacion>> obtenerMedicaciones() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicacionService.obtenerMedicaciones());
    }

    @Operation(summary = "Obtener medicaciones por usuario", description = """
            Retorna una colección con todas las medicaciones filtradas por el id del usuario de la base de datos
            """)
    @GetMapping("/usuario/{id-usuario}")
    public ResponseEntity<List<Medicacion>> obtenerMedicacionesPorUsuario(@PathVariable("id-usuario") String idUsuario,
            @RequestParam(required = false) LocalDate fecha)
            throws InterruptedException, ExecutionException {
        var tratamientos = tratamientoService.obtenerTratamientosPorUsuario(idUsuario);
        var medicaciones = tratamientos.stream()
                .flatMap(tratamiento -> ParseoSeguroUtil.safeObtenerMedicacionesPorParametro(medicacionService,
                        "idTratamiento", tratamiento.getIdTratamiento()))
                .collect(Collectors.toSet()).stream().toList();

        return ResponseEntity.ok(medicaciones);
    }

    @Operation(summary = "Guardar medicación", description = """
            Retorna un objeto de tipo Medicación con los mismos datos ingresados además de incluir el id del objeto guardado en la base de datos
            """)
    @PostMapping("")
    public ResponseEntity<Medicacion> guardarMedicacion(@RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = medicacionService.guardarMedicacion(medicacion);
        var tratamiento = tratamientoService.obtenerTratamiento(medicacion.getIdTratamiento());
        var usuario = usuarioService.obtenerUsuario(tratamiento.getIdUsuario());
        
        var notificacionMedicacion = Notificacion.builder()
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
                    .idMedicacion(medicacion.getIdMedicacion())
                    .idCuidador(usuario.getIdCuidador())
                    .marcaTiempo("9999-12-31T23:59:59")
                    .tipoNotificacion(TipoNotificacion.RECORDATORIO_EXISTENCIA)
                    .build();

            notificacionService.guardarNotificacion(notificacionExistencia);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener medicación por id", description = """
            Retorna el objeto Medicación relacionado con el id dado del documento
            """)
    @GetMapping("/{id-medicacion}")
    public ResponseEntity<Medicacion> obtenerMedicacion(@PathVariable("id-medicacion") String idMedicacion)
            throws InterruptedException, ExecutionException {
        var response = medicacionService.obtenerMedicacion(idMedicacion);

        if (Objects.nonNull(response))
            return ResponseEntity.ok(response);
        else
            throw new ResourceNotFoundException("No se encontró la medicación");
    }

    @Operation(summary = "Actualizar medicación", description = """
            Retorna un objeto de tipo Medicación con los mismos datos ingresados y actualizados
            """)
    @PutMapping("/{id-medicacion}")
    public ResponseEntity<Medicacion> actualizarMedicacion(@PathVariable("id-medicacion") String idMedicacion,
            @RequestBody @Valid Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(medicacionService.actualizarMedicacion(idMedicacion, medicacion));
    }

    @Operation(summary = "Eliminar medicación", description = """
            Retorna un string con el timestamp del momento en el que el registro fue eliminado
            """)
    @DeleteMapping("/{id-medicacion}")
    public ResponseEntity<String> borrarMedicacion(@PathVariable("id-medicacion") String idMedicacion)
            throws InterruptedException, ExecutionException {
        var notificacion = notificacionService.obtenerNotificacionPorParametro("idMedicacion", idMedicacion);
        var response = medicacionService.borrarMedicacion(idMedicacion);
        notificacionService.borrarNotificacion(notificacion.getIdNotificacion());

        return ResponseEntity.ok(response);
    }
}
