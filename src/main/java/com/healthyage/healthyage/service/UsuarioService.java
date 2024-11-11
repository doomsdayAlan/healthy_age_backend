package com.healthyage.healthyage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.cloud.FirestoreClient;
import com.healthyage.healthyage.domain.entity.Usuario;
import com.healthyage.healthyage.exception.DuplicatedObjectException;
import com.healthyage.healthyage.util.OTPGenerator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final EmailService emailService;
    
    private static final String COLECCION = "usuario";

    public List<Usuario> obtenerUsuarios() throws InterruptedException, ExecutionException {
        var usuarios = new ArrayList<Usuario>();
        var bdFirestore = FirestoreClient.getFirestore();
        var futuro = bdFirestore.collection(COLECCION).get();
        var documentos = futuro.get().getDocuments();

        if (documentos != null) {
            for (var documento : documentos) {
                usuarios.add(documento.toObject(Usuario.class));
            }
        }

        return usuarios;
    }

    public Usuario guardarUsuario(Usuario usuario)
            throws InterruptedException, ExecutionException {
        var correo = usuario.getCorreo();
        var correoNoVerificado = "unverified:" + usuario.getCorreo();
        var bdFirestore = FirestoreClient.getFirestore();
        var usuariosExistente = bdFirestore.collection(COLECCION).whereEqualTo("correo", correo)
                .whereEqualTo("correo", correoNoVerificado)
                .whereEqualTo("numero", usuario.getTelefono()).get().get().getDocuments();

        if (!usuariosExistente.isEmpty()) {
            throw new DuplicatedObjectException("El correo o número de teléfono ya está registrado");
        }

        var documento = bdFirestore.collection(COLECCION).document();
        usuario.setIdUsuario(documento.getId());
        usuario.setCorreo(correoNoVerificado);
        var futuro = documento.set(usuario);
        var result = futuro.get();

        if (result != null) {
            emailService.sendEmail(correo, "Tu clave de verificación", OTPGenerator.generateOTP());
            return usuario;
        } else {
            throw new ExecutionException("Error al guardar el usuario: resultado nulo", null);
        }
    }

    public Usuario obtenerUsuario(String idUsuario) throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var referenciaDocumento = bdFirestore.collection(COLECCION).document(idUsuario);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Usuario.class) : null;
    }

    public Usuario actualizarUsuario(String idUsuario, Usuario usuario)
            throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idUsuario);
        usuario.setIdUsuario(idUsuario);
        var futuro = documento.set(usuario);
        var result = futuro.get();

        if (result != null) {
            return usuario;
        } else {
            throw new ExecutionException("Error al actualizar el usuario: resultado nulo", null);
        }
    }

    public String borrarUsuario(String idUsuario) throws InterruptedException, ExecutionException {
        var bdFirestore = FirestoreClient.getFirestore();
        var documento = bdFirestore.collection(COLECCION).document(idUsuario);
        var futuro = documento.delete();
        var result = futuro.get();

        if (result != null) {
            return "Usuario con ID " + idUsuario + " borrado con éxito a las: " + result.getUpdateTime();
        } else {
            throw new ExecutionException("Error al borrar el usuario: resultado nulo", null);
        }
    }
}
