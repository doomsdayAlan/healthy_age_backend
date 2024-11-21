package com.healthyage.healthyage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.healthyage.healthyage.domain.entity.Medicamento;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicamentoService {
    private final Firestore firestore;

    public List<Medicamento> obtenerMedicamentos() throws InterruptedException, ExecutionException {
        var medicamentos = new ArrayList<Medicamento>();
        var futuro = firestore.collection(Medicamento.PATH).get();
        var documentos = futuro.get().getDocuments();

        if (Objects.nonNull(documentos))
            for (var documento : documentos)
                medicamentos.add(documento.toObject(Medicamento.class));

        return medicamentos;
    }

    public Medicamento guardarMedicamento(Medicamento medicamento)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicamento.PATH).document();
        medicamento.setIdMedicamento(documento.getId());
        var futuro = documento.set(medicamento);
        var result = futuro.get();

        if (Objects.nonNull(result))
            return medicamento;
        else
            throw new ExecutionException("Error al guardar el medicamento: resultado nulo", null);
    }

    public Medicamento obtenerMedicamento(String idMedicamento)
            throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Medicamento.PATH).document(idMedicamento);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Medicamento.class) : null;
    }

    public Medicamento actualizarMedicamento(String idMedicamento, Medicamento medicamento)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicamento.PATH).document(idMedicamento);
        medicamento.setIdMedicamento(idMedicamento);
        var futuro = documento.set(medicamento);
        var result = futuro.get();

        if (Objects.nonNull(result))
            return medicamento;
        else
            throw new ExecutionException("Error al actualizar el medicamento: resultado nulo", null);
    }

    public String borrarMedicamento(String idMedicamento) throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Medicamento.PATH).document(idMedicamento);
        var futuro = documento.delete();
        var result = futuro.get();

        if (Objects.nonNull(result))
            return "Medicamento con ID " + idMedicamento + " borrado con éxito a las: "
                    + result.getUpdateTime();
        else
            throw new ExecutionException("Error al borrar el medicamento: resultado nulo", null);
    }
}
