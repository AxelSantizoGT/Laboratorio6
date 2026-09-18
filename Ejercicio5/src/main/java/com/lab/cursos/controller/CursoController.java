package com.lab.cursos.controller;

import com.lab.cursos.dto.CursoRequest;
import com.lab.cursos.dto.CursoResponse;
import com.lab.cursos.model.EstadoCurso;
import com.lab.cursos.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone las operaciones de administracion de cursos.
 */
@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoResponse> crearCurso(@Valid @RequestBody CursoRequest request) {
        CursoResponse creado = cursoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> consultarCursos(
            @RequestParam(required = false) EstadoCurso estado) {
        return ResponseEntity.ok(cursoService.consultarTodos(estado));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CursoResponse> consultarCursoPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(cursoService.consultarPorCodigo(codigo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizarCurso(
            @PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return ResponseEntity.ok(cursoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
