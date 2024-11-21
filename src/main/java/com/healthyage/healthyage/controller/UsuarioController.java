package com.healthyage.healthyage.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

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
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthyage.healthyage.domain.entity.Usuario;
import com.healthyage.healthyage.exception.ResourceNotFoundException;
import com.healthyage.healthyage.service.MedicacionService;
import com.healthyage.healthyage.service.NotificacionService;
import com.healthyage.healthyage.service.TratamientoService;
import com.healthyage.healthyage.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios")
@Tag(name = "Api de usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final NotificacionService notificacionService;
    private final MedicacionService medicacionService;
    private final TratamientoService tratamientoService;

    @Operation(summary = "Obtener todos los usuarios", description = """
            Retorna una colección con todos los usuarios de la base de datos
            """)
    @GetMapping("")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @Operation(summary = "Guardar usuario", description = """
            Retorna un objeto de tipo Usuario con los mismos datos ingresados además de incluir el id del objeto guardado en la base de datos
            """)
    @PostMapping("")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody @Valid Usuario usuario)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(usuarioService.guardarUsuario(usuario));
    }

    @Operation(summary = "Obtener usuario por id", description = """
            Retorna el objeto Usuario relacionado con el id dado del documento
            """)
    @GetMapping("/{id-usuario}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id-usuario") String idUsuario)
            throws InterruptedException, ExecutionException, ResourceNotFoundException {
        var response = usuarioService.obtenerUsuario(idUsuario);

        if (response != null)
            return ResponseEntity.ok(response);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El usuario no existe");
    }

    @Operation(summary = "Actualizar usuario", description = """
            Retorna un objeto de tipo Usuario con los mismos datos ingresados y actualizados
            """)
    @PutMapping("/{id-usuario}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id-usuario") String idUsuario,
            @RequestBody @Valid Usuario usuario)
            throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(idUsuario, usuario));
    }

    @Operation(summary = "Eliminar usuario", description = """
            Retorna un string con el timestamp del momento en el que el registro fue eliminado
            """)
    @DeleteMapping("/{id-usuario}")
    public ResponseEntity<String> borrarUsuario(@PathVariable("id-usuario") String idUsuario)
            throws InterruptedException, ExecutionException {
        var response = usuarioService.borrarUsuario(idUsuario);
        var tratamientos = tratamientoService.obtenerTratamientosPorUsuario(idUsuario);
        var objectMapper = new ObjectMapper();
        var idsMedicaciones = tratamientos.stream()
                .flatMap(tratamiento -> {
                    try {
                        return objectMapper.readValue(
                                tratamiento.getIdMedicaciones(),
                                new TypeReference<List<String>>() {
                                }).stream();
                    } catch (JsonProcessingException e) {
                        return Stream.empty();
                    }

                })
                .toList();
        // TODO: borrar los registros
        return ResponseEntity.ok(response);
    }
}
