package com.healthyage.healthyage.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.healthyage.healthyage.domain.entity.Medicacion;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicacionService {
    private final Firestore firestore;

    public List<Medicacion> obtenerMedicaciones() throws InterruptedException, ExecutionException {
        var medicaciones = new ArrayList<Medicacion>();
        var futuro = firestore.collection(Medicacion.PATH).get();
        var documentos = futuro.get().getDocuments();

        if (documentos != null) {
            for (var documento : documentos) {
                medicaciones.add(documento.toObject(Medicacion.class));
            }
        }

        return medicaciones;
    }

    public List<Medicacion> obtenerMedicacionesporUsuario(String idUsuario, LocalDate fecha) throws InterruptedException, ExecutionException {
        var medicaciones = new ArrayList<Medicacion>();
        var futuro = firestore.collection(Medicacion.PATH).whereEqualTo("idUsuario", idUsuario).get();
        var documentos = futuro.get().getDocuments();

        if (documentos != null) {
            for (var documento : documentos) {
                medicaciones.add(documento.toObject(Medicacion.class));
            }
        }

        return Objects.isNull(fecha) ? medicaciones
                : medicaciones.stream()
                        .filter(medicacion -> (fecha.isAfter(LocalDate.parse(medicacion.getFechaInicio()))
                                || fecha.isEqual(LocalDate.parse(medicacion.getFechaInicio())))
                                && (fecha.isBefore(LocalDate.parse(medicacion.getFechaFin()))
                                        || fecha.isEqual(LocalDate.parse(medicacion.getFechaFin()))))
                        .toList();
    }

    public Medicacion guardarMedicacion(Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicacion.PATH).document();
        medicacion.setIdMedicacion(documento.getId());
        var futuro = documento.set(medicacion);
        var result = futuro.get();

        if (result != null) {
            return medicacion;
        } else {
            throw new ExecutionException("Error al guardar la medicacion: resultado nulo", null);
        }
    }

    public Medicacion obtenerMedicacion(String idMedicacion)
            throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Medicacion.PATH).document(idMedicacion);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Medicacion.class) : null;
    }

    public Medicacion actualizarMedicacion(String idMedicacion, Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicacion.PATH).document(idMedicacion);
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
        var documento = firestore.collection(Medicacion.PATH).document(idMedicacion);
        var futuro = documento.delete();
        var result = futuro.get();

        if (result != null) {
            return "Medicacion con ID " + idMedicacion + " borrado con éxito a las: "
                    + result.getUpdateTime();
        } else {
            throw new ExecutionException("Error al borrar la medicacion: resultado nulo", null);
        }
    }
}
