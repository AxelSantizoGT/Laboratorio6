package com.lab.libros.controller;

import com.lab.libros.dto.LibroRequest;
import com.lab.libros.dto.LibroResponse;
import com.lab.libros.model.EstadoLibro;
import com.lab.libros.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone las operaciones de administracion de libros.
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<LibroResponse> registrarLibro(@Valid @RequestBody LibroRequest request) {
        LibroResponse creado = libroService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<LibroResponse>> consultarLibros(
            @RequestParam(required = false) EstadoLibro estado) {
        return ResponseEntity.ok(libroService.consultarTodos(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> consultarLibroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.consultarPorId(id));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<LibroResponse>> consultarLibroPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(libroService.consultarPorTitulo(titulo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizarLibro(
            @PathVariable Long id, @Valid @RequestBody LibroRequest request) {
        return ResponseEntity.ok(libroService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
