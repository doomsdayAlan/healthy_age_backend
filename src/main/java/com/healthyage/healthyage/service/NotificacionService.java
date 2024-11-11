package com.healthyage.healthyage.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.cloud.FirestoreClient;
import com.healthyage.healthyage.domain.entity.Notificacion;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificacionService {
    private final MedicacionService medicacionService;

    private static final String COLECCION = "notificacion";

    public List<Notificacion> obtenerNotificaciones() throws InterruptedException, ExecutionException {
        var notificaciones = new ArrayList<Notificacion>();
        var bdFirestore = FirestoreClient.getFirestore();
        var futuro = bdFirestore.collection(COLECCION).get();
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
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document();
        notificacion.setIdNotificacion(documento.getId());
        var futuro = documento.set(notificacion);
        var result = futuro.get();

        if (result != null) {
            return notificacion;
        } else {
            throw new ExecutionException("Error al guardar el notificacion: resultado nulo", null);
        }
    }

    public Notificacion obtenerNotificacion(String idNotificacion)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var referenciaDocumento = bdFirestore.collection(COLECCION).document(idNotificacion);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Notificacion.class) : null;
    }

    public Notificacion obtenerNotificacionPorTratamiento(String idTratamiento)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var referenciaDocumentos = bdFirestore.collection(COLECCION).whereEqualTo("idTratamiento", idTratamiento).get()
                .get().getDocuments();

        return referenciaDocumentos.stream()
                .map(doc -> doc.toObject(Notificacion.class)).toList().get(0);
    }

    public Notificacion actualizarNotificacion(String idNotificacion, Notificacion notificacion, boolean pospuesto,
            boolean aceptado)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idNotificacion);
        var medicacion = medicacionService.obtenerMedicacion(notificacion.getIdMedicacion());
        var marcaTiempo = notificacion.getMarcaTiempo();

        if (pospuesto) {
            marcaTiempo = LocalDateTime.parse(notificacion.getMarcaTiempo()).plusMinutes(10).toString();
        } else if (aceptado) {
            marcaTiempo =  MedicacionService.ajustarTiempoNotificacion(LocalDateTime.now(), medicacion.getIntervalo(),
                        medicacion.getTipoIntervalo());
        }

        notificacion.setIdNotificacion(idNotificacion);
        notificacion.setMarcaTiempo(marcaTiempo);

        var futuro = documento.set(notificacion);
        var result = futuro.get();

        if (result != null) {
            return notificacion;
        } else {
            throw new ExecutionException("Error al actualizar el notificacion: resultado nulo", null);
        }
    }

    public String borrarNotificacion(String idNotificacion) throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idNotificacion);
        var futuro = documento.delete();
        var result = futuro.get();

        if (result != null) {
            return "Notificacion con ID " + idNotificacion + " borrado con éxito a las: "
                    + result.getUpdateTime();
        } else {
            throw new ExecutionException("Error al borrar el notificacion: resultado nulo", null);
        }
    }
}
