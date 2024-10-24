package com.healthyage.healthyage.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthyage.healthyage.domain.entity.Usuario;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService servicio;

    @GetMapping("")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.obtenerUsuarios());
    }

    @PostMapping("")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody @Valid Usuario usuario)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.guardarUsuario(usuario));
    }

    @GetMapping("/{id-usuario}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id-usuario") String idUsuario)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = servicio.obtenerUsuario(idUsuario);
        
        if (response != null)
            return ResponseEntity.ok(response);
        else 
            throw ResourceNotFoundException.createWith("medicamento");
    }

    @PutMapping("/{id-usuario}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id-usuario") String idUsuario, @RequestBody @Valid Usuario usuario)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.actualizarUsuario(idUsuario, usuario));
    }

    @DeleteMapping("/{id-usuario}")
    public ResponseEntity<String> borrarUsuario(@PathVariable("id-usuario") String idUsuario)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(servicio.borrarUsuario(idUsuario));
    }
}
