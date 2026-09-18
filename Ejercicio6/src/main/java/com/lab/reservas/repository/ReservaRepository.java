package com.lab.reservas.repository;

import com.lab.reservas.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio en memoria para el almacenamiento de reservas.
 */
@Repository
public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    public Reserva guardar(Reserva reserva) {
        reserva.setId(secuenciaId.incrementAndGet());
        reservas.add(reserva);
        return reserva;
    }

    public List<Reserva> buscarTodos() {
        return new ArrayList<>(reservas);
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }
}
