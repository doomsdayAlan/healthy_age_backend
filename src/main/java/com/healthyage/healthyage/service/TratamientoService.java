package com.healthyage.healthyage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.cloud.FirestoreClient;
import com.healthyage.healthyage.domain.Tratamiento;

@Service
public class TratamientoService {
    private static final String COLECCION = "tratamiento";

    public List<Tratamiento> obtenerTratamientos() throws InterruptedException, ExecutionException {
        var tratamientos = new ArrayList<Tratamiento>();
        var bdFirestore = FirestoreClient.getFirestore();
        var futuro = bdFirestore.collection(COLECCION).get();
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
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document();
        tratamiento.setIdTratamiento(documento.getId());
        var futuro = documento.set(tratamiento);
        var result = futuro.get();

        if (result != null) {
            return tratamiento;
        } else {
            throw new ExecutionException("Error al guardar el tratamiento: resultado nulo", null);
        }
    }

    public Tratamiento obtenerTratamiento(String idMedicamento)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var referenciaDocumento = bdFirestore.collection(COLECCION).document(idMedicamento);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Tratamiento.class) : null;
    }

    public Tratamiento actualizarTratamiento(String idMedicamento, Tratamiento tratamiento)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idMedicamento);
        tratamiento.setIdTratamiento(idMedicamento);
        var futuro = documento.set(tratamiento);
        var result = futuro.get();

        if (result != null) {
            return tratamiento;
        } else {
            throw new ExecutionException("Error al actualizar el tratamiento: resultado nulo", null);
        }
    }

    public String borrarTratamiento(String idMedicamento) throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idMedicamento);
        var futuro = documento.delete();
        var result = futuro.get();

        if (result != null) {
            return "Tratamiento con ID " + idMedicamento + " borrado con éxito a las: "
                    + result.getUpdateTime();
        } else {
            throw new ExecutionException("Error al borrar el tratamiento: resultado nulo", null);
        }
    }
}
