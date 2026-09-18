package com.lab.libros.exception;

/**
 * Excepcion lanzada cuando un recurso solicitado no existe.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
