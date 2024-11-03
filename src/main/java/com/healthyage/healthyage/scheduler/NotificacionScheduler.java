package com.healthyage.healthyage.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
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
    private final TratamientoService tratamientoService;
    private final NotificacionService notificacionService;
    private final UsuarioService usuarioService;
    private final MedicamentoService medicamentoService;

    @Scheduled(fixedRate = 60000)
    private final void enviarNotificaciones() throws InterruptedException, ExecutionException {
        var tratamientos = tratamientoService.obtenerTratamientos();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (var tratamiento : tratamientos) {
            var notificacion = notificacionService.obtenerNotificacionPorTratamiento(tratamiento.getIdTratamiento());
            var marcaTiempo = LocalDateTime.parse(notificacion.getMarcaTiempo(), formatter);
            var tiempoActual = LocalDateTime.now();
            var tokenUsuario = usuarioService.obtenerUsuario(tratamiento.getIdUsuario()).getTokenUsuario();
            var medicamento = medicamentoService.obtenerMedicamento(tratamiento.getIdMedicamento()).getNombreMedicamento();

            if ((tiempoActual.isAfter(marcaTiempo) || tiempoActual.isEqual(marcaTiempo))
                    && tiempoActual.isBefore(marcaTiempo.plusHours(tratamiento.getIntervalo()))) {
                notificacion.setMarcaTiempo(marcaTiempo.plusHours(tratamiento.getIntervalo()).toString());
                notificacionService.actualizarNotificacion(notificacion.getIdNotificacion(), notificacion);

                enviarNotificacion(tokenUsuario, "Recordatorio de Tratamiento", String.format("Medicamento: %s %n Dosis: %s", medicamento, tratamiento.getDosis()));
            }
        }
    }

    private void enviarNotificacion(String tokenUsuario, String titulo, String cuerpo) {
        var message = Message.builder()
                .setNotification(Notification.builder().setTitle(titulo).setBody(cuerpo).build())
                .setToken(tokenUsuario)
                .build();
        var future = FirebaseMessaging.getInstance().sendAsync(message);

        ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
            @Override
            public void onSuccess(String response) {
                log.info("Notificación enviada a: " + tokenUsuario + ". ID de mensaje: " + response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error al enviar notificación: " + throwable.getMessage());
            }
        }, MoreExecutors.directExecutor());
    }
}
