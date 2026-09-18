package com.lab.reservas.exception;

/**
 * Excepcion lanzada cuando la reserva viola una regla de negocio,
 * por ejemplo cuando la fecha de salida es anterior a la de entrada.
 */
public class SolicitudInvalidaException extends RuntimeException {
    public SolicitudInvalidaException(String message) {
        super(message);
    }
}
