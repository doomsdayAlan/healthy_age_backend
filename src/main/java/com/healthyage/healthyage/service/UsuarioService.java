package com.healthyage.healthyage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.healthyage.healthyage.domain.dto.UsuarioUtilDTO;
import com.healthyage.healthyage.domain.entity.Usuario;
import com.healthyage.healthyage.exception.DuplicatedObjectException;
import com.healthyage.healthyage.util.GeneradorOTPUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final Firestore firestore;
    private final EmailService emailService;

    private static final String CORREO = "correo";

    public List<Usuario> obtenerUsuarios() throws InterruptedException, ExecutionException {
        var usuarios = new ArrayList<Usuario>();
        var futuro = firestore.collection(Usuario.PATH).get();
        var documentos = futuro.get().getDocuments();

        if (Objects.nonNull(documentos))
            for (var documento : documentos)
                usuarios.add(documento.toObject(Usuario.class));

        return usuarios;
    }

    public Usuario guardarUsuario(Usuario usuario)
            throws InterruptedException, ExecutionException {
        var correo = usuario.getCorreo();
        var otp = GeneradorOTPUtil.generarOTP();
        var usuariosExistente = firestore.collection(Usuario.PATH).whereArrayContains(CORREO, correo)
                .whereEqualTo("numero", usuario.getTelefono()).get().get().getDocuments();

        if (!usuariosExistente.isEmpty())
            throw new DuplicatedObjectException("El correo o número de teléfono ya está registrado");

        var documento = firestore.collection(Usuario.PATH).document();
        usuario.setIdUsuario(documento.getId());
        usuario.setCorreo(String.format("unverified:%s:%s", otp, correo));
        var futuro = documento.set(usuario);
        var result = futuro.get();

        if (Objects.nonNull(result)) {
            emailService.enviarEmail(correo, "Tu clave de verificación", otp);
            return usuario;
        } else {
            throw new ExecutionException("Error al guardar el usuario: resultado nulo", null);
        }
    }

    public Usuario obtenerUsuario(String idUsuario) throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Usuario.PATH).document(idUsuario);
        var futuro = referenciaDocumento.get();
        var documento = futuro.get();

        return documento.exists() ? documento.toObject(Usuario.class) : null;
    }

    public Usuario actualizarUsuario(String idUsuario, Usuario usuario)
            throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Usuario.PATH).document(idUsuario);
        usuario.setIdUsuario(idUsuario);
        var futuro = documento.set(usuario);
        var result = futuro.get();

        if (Objects.nonNull(result))
            return usuario;
        else
            throw new ExecutionException("Error al actualizar el usuario: resultado nulo", null);
    }

    public String borrarUsuario(String idUsuario) throws InterruptedException, ExecutionException {
        var documento = firestore.collection(Usuario.PATH).document(idUsuario);
        var futuro = documento.delete();
        var result = futuro.get();

        if (Objects.nonNull(result))
            return "Usuario con ID " + idUsuario + " borrado con éxito a las: " + result.getUpdateTime();
        else
            throw new ExecutionException("Error al borrar el usuario: resultado nulo", null);
    }

    public Usuario login(UsuarioUtilDTO usuarioUtilDTO) throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Usuario.PATH).whereEqualTo(CORREO, usuarioUtilDTO.getCorreo())
                .whereEqualTo("pinSeguridad", usuarioUtilDTO.getPinSeguridad());
        var futuro = referenciaDocumento.get();
        var documentos = futuro.get().getDocuments();

        return documentos.isEmpty() ? null : documentos.get(0).toObject(Usuario.class);
    }

    public Usuario confirmarCorreo(UsuarioUtilDTO usuarioUtilDTO) throws InterruptedException, ExecutionException {
        var referenciaDocumento = firestore.collection(Usuario.PATH).whereEqualTo(CORREO, usuarioUtilDTO.getCorreo());
        var futuro = referenciaDocumento.get();
        var documentos = futuro.get().getDocuments();

        if (documentos.isEmpty())
            return null;
        
        var usuario = documentos.get(0).toObject(Usuario.class);

        if (usuario.getCorreo().contains("unverified")) {
            var info = usuario.getCorreo().split(":");

            if (!info[1].equals(usuarioUtilDTO.getCodigoConfirmacion()))
                return null;
            
            usuario.setCorreo(info[2]);
            usuario = actualizarUsuario(usuario.getIdUsuario(), usuario);
        }

        return usuario;
    } 
}
