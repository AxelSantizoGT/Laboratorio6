package com.lab.libros.exception;

/**
 * Excepcion lanzada cuando se intenta crear un recurso que ya existe (ej. ISBN duplicado).
 */
public class RecursoDuplicadoException extends RuntimeException {
    public RecursoDuplicadoException(String message) {
        super(message);
    }
}
