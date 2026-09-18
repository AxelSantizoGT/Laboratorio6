package com.lab.reservas.service;

import com.lab.reservas.dto.ReservaRequest;
import com.lab.reservas.dto.ReservaResponse;
import com.lab.reservas.exception.RecursoNoEncontradoException;
import com.lab.reservas.exception.SolicitudInvalidaException;
import com.lab.reservas.model.EstadoReserva;
import com.lab.reservas.model.Reserva;
import com.lab.reservas.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacion del servicio de reservas. Contiene las reglas de negocio
 * y delega el almacenamiento al repositorio en memoria.
 */
@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public ReservaResponse crear(ReservaRequest request) {
        validarFechas(request.getFechaEntrada(), request.getFechaSalida());

        Reserva reserva = new Reserva(
                null,
                request.getNombreCliente(),
                request.getHabitacion(),
                request.getFechaEntrada(),
                request.getFechaSalida(),
                request.getEstado());

        Reserva guardada = reservaRepository.guardar(reserva);
        return aResponse(guardada);
    }

    @Override
    public List<ReservaResponse> consultarTodos(EstadoReserva estado) {
        return reservaRepository.buscarTodos().stream()
                .filter(r -> estado == null || r.getEstado() == estado)
                .map(this::aResponse)
                .toList();
    }

    @Override
    public ReservaResponse consultarPorId(Long id) {
        Reserva reserva = reservaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva con id " + id + " no encontrada"));
        return aResponse(reserva);
    }

    @Override
    public ReservaResponse actualizar(Long id, ReservaRequest request) {
        validarFechas(request.getFechaEntrada(), request.getFechaSalida());

        Reserva reserva = reservaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva con id " + id + " no encontrada"));

        reserva.setNombreCliente(request.getNombreCliente());
        reserva.setHabitacion(request.getHabitacion());
        reserva.setFechaEntrada(request.getFechaEntrada());
        reserva.setFechaSalida(request.getFechaSalida());
        reserva.setEstado(request.getEstado());

        return aResponse(reserva);
    }

    @Override
    public ReservaResponse cancelar(Long id) {
        Reserva reserva = reservaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva con id " + id + " no encontrada"));
        reserva.setEstado(EstadoReserva.CANCELADA);
        return aResponse(reserva);
    }

    private void validarFechas(java.time.LocalDate fechaEntrada, java.time.LocalDate fechaSalida) {
        if (fechaSalida.isBefore(fechaEntrada)) {
            throw new SolicitudInvalidaException("La fecha de salida no puede ser anterior a la fecha de entrada");
        }
    }

    private ReservaResponse aResponse(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getNombreCliente(),
                reserva.getHabitacion(),
                reserva.getFechaEntrada(),
                reserva.getFechaSalida(),
                reserva.getEstado());
    }
}
