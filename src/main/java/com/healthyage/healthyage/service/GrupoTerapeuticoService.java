package com.healthyage.healthyage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.cloud.FirestoreClient;
import com.healthyage.healthyage.domain.GrupoTerapeutico;

@Service
public class GrupoTerapeuticoService {
    private static final String COLECCION = "grupo_terapeutico";

    public List<GrupoTerapeutico> obtenerGruposTerapeuticos() throws InterruptedException, ExecutionException {
        var gruposTerapeuticos = new ArrayList<GrupoTerapeutico>();
        var bdFirestore = FirestoreClient.getFirestore();
        var futuro = bdFirestore.collection(COLECCION).get();
        var documentos = futuro.get().getDocuments();

        if (documentos != null) {
            for (var documento : documentos) {
                gruposTerapeuticos.add(documento.toObject(GrupoTerapeutico.class));
            }
        }

        return gruposTerapeuticos;
    }

    public GrupoTerapeutico guardGrupoTerapeutico(GrupoTerapeutico grupoTerapeutico)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document();
        grupoTerapeutico.setIdGrupoTerapeutico(documento.getId());
        var futuro = documento.set(grupoTerapeutico);
        var result = futuro.get();

        if (result != null) {
            return grupoTerapeutico;
        } else {
            throw new ExecutionException("Error al guardar el grupoTerapeutico: resultado nulo", null);
        }
    }

    public GrupoTerapeutico obtenGrupoTerapeutico(String idGrupoTerapeutico)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var referenciaDocumento = bdFirestore.collection(COLECCION).document(idGrupoTerapeutico);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(GrupoTerapeutico.class) : null;
    }

    public GrupoTerapeutico actualizarGrupoTerapeutico(String idGrupoTerapeutico, GrupoTerapeutico grupoTerapeutico)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idGrupoTerapeutico);
        grupoTerapeutico.setIdGrupoTerapeutico(idGrupoTerapeutico);
        var futuro = documento.set(grupoTerapeutico);
        var result = futuro.get();

        if (result != null) {
            return grupoTerapeutico;
        } else {
            throw new ExecutionException("Error al actualizar el grupoTerapeutico: resultado nulo", null);
        }
    }

    public String borrarGrupoTerapeutico(String idGrupoTerapeutico) throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idGrupoTerapeutico);
        var futuro = documento.delete();
        var result = futuro.get();

        if (result != null) {
            return "GrupoTerapeutico con ID " + idGrupoTerapeutico + " borrado con éxito a las: "
                    + result.getUpdateTime();
        } else {
            throw new ExecutionException("Error al borrar el grupoTerapeutico: resultado nulo", null);
        }
    }
}
