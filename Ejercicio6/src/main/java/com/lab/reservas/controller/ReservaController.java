package com.lab.reservas.controller;

import com.lab.reservas.dto.ReservaRequest;
import com.lab.reservas.dto.ReservaResponse;
import com.lab.reservas.model.EstadoReserva;
import com.lab.reservas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone las operaciones de administracion de reservas.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> crearReserva(@Valid @RequestBody ReservaRequest request) {
        ReservaResponse creada = reservaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> consultarReservas(
            @RequestParam(required = false) EstadoReserva estado) {
        return ResponseEntity.ok(reservaService.consultarTodos(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> consultarReservaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.consultarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> actualizarReserva(
            @PathVariable Long id, @Valid @RequestBody ReservaRequest request) {
        return ResponseEntity.ok(reservaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservaResponse> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }
}
