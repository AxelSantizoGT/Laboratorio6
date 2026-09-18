package com.lab.libros.service;

import com.lab.libros.dto.LibroRequest;
import com.lab.libros.dto.LibroResponse;
import com.lab.libros.model.EstadoLibro;

import java.util.List;

/**
 * Contrato de servicio para las operaciones de negocio sobre libros.
 */
public interface LibroService {

    LibroResponse registrar(LibroRequest request);

    List<LibroResponse> consultarTodos(EstadoLibro estado);

    LibroResponse consultarPorId(Long id);

    List<LibroResponse> consultarPorTitulo(String titulo);

    LibroResponse actualizar(Long id, LibroRequest request);

    void eliminar(Long id);
}
