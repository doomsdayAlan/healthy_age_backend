package com.healthyage.healthyage.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthyage.healthyage.domain.entity.Medicacion;
import com.healthyage.healthyage.domain.entity.Notificacion;
import com.healthyage.healthyage.domain.enumeration.TipoIntervalo;
import com.healthyage.healthyage.domain.enumeration.TipoNotificacion;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicacionService {
    private final TratamientoService tratamientoService;
    private final NotificacionService notificacionService;
    private final UsuarioService usuarioService;

    private static final String COLECCION = "medicacion";

    public List<Medicacion> obtenerMedicaciones() throws InterruptedException, ExecutionException {
        var medicaciones = new ArrayList<Medicacion>();
        var bdFirestore = FirestoreClient.getFirestore();
        var futuro = bdFirestore.collection(COLECCION).get();
        var documentos = futuro.get().getDocuments();

        if (documentos != null) {
            for (var documento : documentos) {
                medicaciones.add(documento.toObject(Medicacion.class));
            }
        }

        return medicaciones;
    }

    public Medicacion guardarMedicacion(Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document();
        medicacion.setIdMedicacion(documento.getId());
        var futuro = documento.set(medicacion);
        var result = futuro.get();

        if (result != null) {
            return medicacion;
        } else {
            throw new ExecutionException("Error al guardar la medicacion: resultado nulo", null);
        }
    }

    public List<String> guardarMedicacionPorTratamiento(String idTratamiento, Medicacion nuevaMedicacion)
            throws InterruptedException, ExecutionException {
        var medicacion = guardarMedicacion(nuevaMedicacion);
        var tratamiento = tratamientoService.obtenerTratamiento(idTratamiento);
        var usuario = usuarioService.obtenerUsuario(tratamiento.getIdUsuario());
        var gson = new Gson();
        var medicaciones = new ArrayList<String>(
                gson.fromJson(tratamiento.getIdMedicaciones(), new TypeToken<List<String>>() {
                }.getType()));

        medicaciones.add(medicacion.getIdMedicacion());
        tratamiento.setIdMedicaciones(gson.toJson(medicaciones));
        tratamientoService.actualizarTratamiento(idTratamiento, tratamiento);

        var marcaTiempo = TipoNotificacion.RECORDATORIO_MEDICACION;
        var notificacionMedicacion = Notificacion.builder()
                .idTratamiento(idTratamiento)
                .idMedicacion(medicacion.getIdMedicacion())
                .idCuidador(usuario.getIdCuidador())
                .marcaTiempo(ajustarTiempoNotificacion(
                        LocalDateTime.parse(medicacion.getFechaInicio() + "T" + medicacion.getHoraInicio()),
                        medicacion.getIntervalo(), medicacion.getTipoIntervalo()))
                .tipoNotificacion(marcaTiempo)
                .build();

        notificacionService.guardarNotificacion(notificacionMedicacion);

        if (medicacion.getRecordatorio() == 1) {
            var notificacionExistencia = Notificacion.builder()
                    .idTratamiento(idTratamiento)
                    .idMedicacion(medicacion.getIdMedicacion())
                    .idCuidador(usuario.getIdCuidador())
                    .marcaTiempo("9999-12-31T23:59:59")
                    .tipoNotificacion(marcaTiempo)
                    .build();

            notificacionService.guardarNotificacion(notificacionExistencia);
        }

        return medicaciones;
    }

    public Medicacion obtenerMedicacion(String idMedicacion)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var referenciaDocumento = bdFirestore.collection(COLECCION).document(idMedicacion);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Medicacion.class) : null;
    }

    public Medicacion actualizarMedicacion(String idMedicacion, Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idMedicacion);
        medicacion.setIdMedicamento(idMedicacion);
        var futuro = documento.set(medicacion);
        var result = futuro.get();

        if (result != null) {
            return medicacion;
        } else {
            throw new ExecutionException("Error al actualizar la medicacion: resultado nulo", null);
        }
    }

    public String borrarMedicacion(String idMedicacion) throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idMedicacion);
        var futuro = documento.delete();
        var result = futuro.get();

        if (result != null) {
            return "Medicacion con ID " + idMedicacion + " borrado con éxito a las: "
                    + result.getUpdateTime();
        } else {
            throw new ExecutionException("Error al borrar la medicacion: resultado nulo", null);
        }
    }

    public static String ajustarTiempoNotificacion(LocalDateTime marcaTiempo, int intervalo, TipoIntervalo tipoIntervalo) {
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
