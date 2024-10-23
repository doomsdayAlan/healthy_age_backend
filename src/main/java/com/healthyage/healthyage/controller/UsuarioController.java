package com.healthyage.healthyage.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthyage.healthyage.domain.Usuario;
import com.healthyage.healthyage.service.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService servicio;

    @GetMapping("")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerUsuarios(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.guardarUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/{id-usuario}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id-usuario") String idUsuario)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.obtenerUsuario(idUsuario), HttpStatus.OK);
    }

    @PutMapping("/{id-usuario}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id-usuario") String idUsuario, @RequestBody Usuario usuario)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.actualizarUsuario(idUsuario, usuario), HttpStatus.OK);
    }

    @DeleteMapping("/{id-usuario}")
    public ResponseEntity<String> borrarUsuario(@PathVariable("id-usuario") String idUsuario)
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<>(servicio.borrarUsuario(idUsuario), HttpStatus.OK);
    }
}
