package com.healthyage.healthyage.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.healthyage.healthyage.domain.enumeration.TipoNotificacion;
import com.healthyage.healthyage.service.MedicacionService;
import com.healthyage.healthyage.service.MedicamentoService;
import com.healthyage.healthyage.service.NotificacionService;
import com.healthyage.healthyage.service.TratamientoService;
import com.healthyage.healthyage.service.UsuarioService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class NotificacionScheduler {
    private final NotificacionService notificacionService;
    private final TratamientoService tratamientoService;
    private final MedicacionService medicacionService;
    private final MedicamentoService medicamentoService;
    private final UsuarioService usuarioService;

    @Scheduled(fixedRate = 60000)
    @Cacheable
    private final void enviarNotificaciones() throws InterruptedException, ExecutionException {
        var notificaciones = notificacionService.obtenerNotificaciones();
        var tiempoActual = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (var notificacion : notificaciones) {
            var marcaTiempo = LocalDateTime.parse(notificacion.getMarcaTiempo(), formatter);

            var medicacion = medicacionService.obtenerMedicacion(notificacion.getIdMedicacion());
            var tratamiento = tratamientoService.obtenerTratamiento(notificacion.getIdTratamiento());
            var usuario = usuarioService.obtenerUsuario(tratamiento.getIdUsuario());
            var cuidador = usuarioService.obtenerUsuario(notificacion.getIdCuidador());
            var mensaje = new HashMap<String, String>();
            var tokenUsuario = notificacion.getTipoNotificacion() == TipoNotificacion.RECORDATORIO_ADULTO_MAYOR
                    ? usuario.getTokenUsuario()
                    : cuidador.getTokenUsuario();

            mensaje.put("adulto", usuario.getNombre());
            mensaje.put("tratamiento", tratamiento.getNombreTratamiento());
            mensaje.put("medicamento", medicamentoService.obtenerMedicamento(medicacion.getIdMedicamento())
                    .getNombreMedicamento());
            mensaje.put("administracion", medicacion.getViaAdministracion().name());
            mensaje.put("dosis", medicacion.getDosis() + " " + medicacion.getUnidad());
            mensaje.put("cuidador", cuidador.getNombre());

            var cuerpo = new Gson().toJson(mensaje);

            if ((tiempoActual.isAfter(marcaTiempo) || tiempoActual.isEqual(marcaTiempo))
                    && tiempoActual.isBefore(marcaTiempo.plusHours(medicacion.getIntervalo()))) {
                notificacionService.actualizarNotificacion(notificacion.getIdNotificacion(), notificacion, true, false);
                enviarNotificacion(tokenUsuario, notificacion.getTipoNotificacion().name(), cuerpo);
            }
        }
    }

    private void enviarNotificacion(String tokenUsuario, String titulo, String cuerpo) {
        var message = Message.builder()
                .setNotification(Notification.builder().setTitle(titulo).setBody(cuerpo).build())
                .setToken(tokenUsuario)
                .build();
        var future = FirebaseMessaging.getInstance().sendAsync(message);

        ApiFutures.addCallback(future, new ApiFutureCallback<>() {
            @Override
            public void onSuccess(String response) {
                // Se omite la notificación de éxito para evitar saturación de logs
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error al enviar notificación: {}", throwable.getMessage());
            }
        }, MoreExecutors.directExecutor());
    }
}
