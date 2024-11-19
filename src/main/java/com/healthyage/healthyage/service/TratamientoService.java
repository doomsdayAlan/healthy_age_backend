package com.healthyage.healthyage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.healthyage.healthyage.domain.entity.Tratamiento;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TratamientoService {
    private final Firestore firestore;

    public List<Tratamiento> obtenerTratamientos() throws InterruptedException, ExecutionException {
        var tratamientos = new ArrayList<Tratamiento>();
        var futuro = firestore.collection(Tratamiento.PATH).get();
        var documentos = futuro.get().getDocuments();

        if (documentos != null) {
            for (var documento : documentos) {
                tratamientos.add(documento.toObject(Tratamiento.class));
            }
        }

        return tratamientos;
    }

    public Tratamiento guardarTratamiento(Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Tratamiento.PATH).document();
        tratamiento.setIdTratamiento(documento.getId());
        var futuro = documento.set(tratamiento);
        var result = futuro.get();

        if (result != null) {
            return tratamiento;
        } else {
            throw new ExecutionException("Error al guardar el tratamiento: resultado nulo", null);
        }
    }

    public Tratamiento obtenerTratamiento(String idTratamiento)
            throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Tratamiento.PATH).document(idTratamiento);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Tratamiento.class) : null;
    }

    public List<Tratamiento> obtenerTratamientosPorUsuario(String idUsuario)
            throws InterruptedException, ExecutionException {
        var referenciaDocumentos = firestore.collection(Tratamiento.PATH).whereEqualTo("idUsuario", idUsuario).get()
                .get().getDocuments();

        return referenciaDocumentos.stream()
                .map(doc -> doc.toObject(Tratamiento.class)).toList();
    }

    public Tratamiento actualizarTratamiento(String idTratamiento, Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Tratamiento.PATH).document(idTratamiento);
        tratamiento.setIdTratamiento(idTratamiento);
        var futuro = documento.set(tratamiento);
        var result = futuro.get();

        if (result != null) {
            return tratamiento;
        } else {
            throw new ExecutionException("Error al actualizar el tratamiento: resultado nulo", null);
        }
    }

    public String borrarTratamiento(String idTratamiento) throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Tratamiento.PATH).document(idTratamiento);
        var futuro = documento.delete();
        var result = futuro.get();

        if (result != null) {
            return "Tratamiento con ID " + idTratamiento + " borrado con éxito a las: "
                    + result.getUpdateTime();
        } else {
            throw new ExecutionException("Error al borrar el tratamiento: resultado nulo", null);
        }
    }
}
