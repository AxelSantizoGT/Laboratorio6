package com.lab.reservas.service;

import com.lab.reservas.dto.ReservaRequest;
import com.lab.reservas.dto.ReservaResponse;
import com.lab.reservas.model.EstadoReserva;

import java.util.List;

/**
 * Contrato de servicio para las operaciones de negocio sobre reservas.
 */
public interface ReservaService {

    ReservaResponse crear(ReservaRequest request);

    List<ReservaResponse> consultarTodos(EstadoReserva estado);

    ReservaResponse consultarPorId(Long id);

    ReservaResponse actualizar(Long id, ReservaRequest request);

    ReservaResponse cancelar(Long id);
}
