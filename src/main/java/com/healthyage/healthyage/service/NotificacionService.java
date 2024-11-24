package com.healthyage.healthyage.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.domain.enumeration.TipoIntervalo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificacionService {
    private final Firestore firestore;

    public List<Notificacion> obtenerNotificaciones() throws InterruptedException, ExecutionException {
        var notificaciones = new ArrayList<Notificacion>();
        var futuro = firestore.collection(Notificacion.PATH).get();
        var documentos = futuro.get().getDocuments();

        if (documentos != null) {
            for (var documento : documentos) {
                notificaciones.add(documento.toObject(Notificacion.class));
            }
        }

        return notificaciones;
    }

    public Notificacion guardarNotificacion(Notificacion notificacion)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Notificacion.PATH).document();
        notificacion.setIdNotificacion(documento.getId());
        var futuro = documento.set(notificacion);
        var result = futuro.get();

        if (Objects.nonNull(result))
            return notificacion;
        else
            throw new ExecutionException("Error al guardar el notificacion: resultado nulo", null);
    }

    public Notificacion obtenerNotificacion(String idNotificacion)
            throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Notificacion.PATH).document(idNotificacion);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Notificacion.class) : null;
    }

    public Notificacion obtenerNotificacionPorParametro(String parametro, String valor)
            throws InterruptedException, ExecutionException {
        var documentos = obtenerNotificacionesPorParametro(parametro, valor);
        
        return documentos.isEmpty() ? documentos.get(0) : null;
    }

    public List<Notificacion> obtenerNotificacionesPorParametro(String parametro, String valor)
            throws InterruptedException, ExecutionException {
        var referenciaDocumentos = firestore.collection(Notificacion.PATH).whereEqualTo(parametro, valor)
                .get()
                .get().getDocuments();

        return referenciaDocumentos.stream()
                .map(doc -> doc.toObject(Notificacion.class)).toList();
    }

    public Notificacion actualizarNotificacion(String idNotificacion, Notificacion notificacion, boolean pospuesto,
            boolean aceptado)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Notificacion.PATH).document(idNotificacion);
        var marcaTiempo = notificacion.getMarcaTiempo();

        if (pospuesto)
            marcaTiempo = LocalDateTime.parse(notificacion.getMarcaTiempo()).plusMinutes(10).toString();
        else if (aceptado)
            marcaTiempo = ajustarTiempoNotificacion(LocalDateTime.now(), notificacion.getIntervalo(),
                    notificacion.getTipoIntervalo());

        notificacion.setIdNotificacion(idNotificacion);
        notificacion.setMarcaTiempo(marcaTiempo);

        var futuro = documento.set(notificacion);
        var result = futuro.get();

        if (Objects.nonNull(result))
            return notificacion;
        else
            throw new ExecutionException("Error al actualizar el notificacion: resultado nulo", null);
    }

    public String borrarNotificacion(String idNotificacion) throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Notificacion.PATH).document(idNotificacion);
        var futuro = documento.delete();
        var result = futuro.get();

        if (Objects.nonNull(result))
            return "Notificacion con ID " + idNotificacion + " borrado con éxito a las: "
                    + result.getUpdateTime();
        else
            throw new ExecutionException("Error al borrar el notificacion: resultado nulo", null);
    }

    public static String ajustarTiempoNotificacion(LocalDateTime marcaTiempo, int intervalo,
            TipoIntervalo tipoIntervalo) {
        ChronoUnit unidad;

        switch (tipoIntervalo) {
            case MINUTOS -> unidad = ChronoUnit.MINUTES;
            case HORAS -> unidad = ChronoUnit.HOURS;
            case DIAS -> unidad = ChronoUnit.DAYS;
            case SEMANAS -> unidad = ChronoUnit.WEEKS;
            default -> throw new IllegalArgumentException("Tipo de intervalo no soportado");
        }

        return marcaTiempo.plus(intervalo, unidad).toString();
    }
}
