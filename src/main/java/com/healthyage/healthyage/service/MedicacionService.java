package com.healthyage.healthyage.service;

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

        if (Objects.nonNull(documentos))
            for (var documento : documentos)
                medicaciones.add(documento.toObject(Medicacion.class));

        return medicaciones;
    }

    public Medicacion guardarMedicacion(Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicacion.PATH).document();
        medicacion.setIdMedicacion(documento.getId());
        var futuro = documento.set(medicacion);
        var result = futuro.get();

        if (Objects.nonNull(result))
            return medicacion;
        else
            throw new ExecutionException("Error al guardar la medicacion: resultado nulo", null);
    }

    public Medicacion obtenerMedicacion(String idMedicacion)
            throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Medicacion.PATH).document(idMedicacion);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Medicacion.class) : null;
    }

    public Medicacion obtenerMedicacionPorParametro(String parametro, String valor)
            throws InterruptedException, ExecutionException {
        var documentos = obtenerMedicacionesPorParametro(parametro, valor);

        return documentos.isEmpty() ? documentos.get(0) : null;
    }

    public List<Medicacion> obtenerMedicacionesPorParametro(String parametro, String valor)
            throws InterruptedException, ExecutionException {
        var referenciaDocumentos = firestore.collection(Medicacion.PATH).whereEqualTo(parametro, valor)
                .get()
                .get().getDocuments();

        return referenciaDocumentos.stream()
                .map(doc -> doc.toObject(Medicacion.class)).toList();
    }

    public Medicacion actualizarMedicacion(String idMedicacion, Medicacion medicacion)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicacion.PATH).document(idMedicacion);
        medicacion.setIdMedicamento(idMedicacion);
        var futuro = documento.set(medicacion);
        var result = futuro.get();

        if (Objects.nonNull(result))
            return medicacion;
        else
            throw new ExecutionException("Error al actualizar la medicacion: resultado nulo", null);
    }

    public String borrarMedicacion(String idMedicacion) throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicacion.PATH).document(idMedicacion);
        var futuro = documento.delete();
        var result = futuro.get();

        if (Objects.nonNull(result))
            return "Medicacion con ID " + idMedicacion + " borrado con éxito a las: "
                    + result.getUpdateTime();
        else
            throw new ExecutionException("Error al borrar la medicacion: resultado nulo", null);
    }
}
