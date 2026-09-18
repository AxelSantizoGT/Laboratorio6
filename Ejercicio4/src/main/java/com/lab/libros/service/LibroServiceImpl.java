package com.lab.libros.service;

import com.lab.libros.dto.LibroRequest;
import com.lab.libros.dto.LibroResponse;
import com.lab.libros.exception.RecursoDuplicadoException;
import com.lab.libros.exception.RecursoNoEncontradoException;
import com.lab.libros.model.EstadoLibro;
import com.lab.libros.model.Libro;
import com.lab.libros.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacion del servicio de libros. Contiene las reglas de negocio
 * y delega el almacenamiento al repositorio en memoria.
 */
@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public LibroResponse registrar(LibroRequest request) {
        libroRepository.buscarPorIsbn(request.getIsbn()).ifPresent(l -> {
            throw new RecursoDuplicadoException("Ya existe un libro registrado con el ISBN " + request.getIsbn());
        });

        Libro libro = new Libro(
                null,
                request.getTitulo(),
                request.getAutor(),
                request.getIsbn(),
                request.getAnioPublicacion(),
                request.getEstado());

        Libro guardado = libroRepository.guardar(libro);
        return aResponse(guardado);
    }

    @Override
    public List<LibroResponse> consultarTodos(EstadoLibro estado) {
        return libroRepository.buscarTodos().stream()
                .filter(l -> estado == null || l.getEstado() == estado)
                .map(this::aResponse)
                .toList();
    }

    @Override
    public LibroResponse consultarPorId(Long id) {
        Libro libro = libroRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro con id " + id + " no encontrado"));
        return aResponse(libro);
    }

    @Override
    public List<LibroResponse> consultarPorTitulo(String titulo) {
        List<Libro> encontrados = libroRepository.buscarPorTitulo(titulo);
        if (encontrados.isEmpty()) {
            throw new RecursoNoEncontradoException("No se encontraron libros con titulo que contenga: " + titulo);
        }
        return encontrados.stream().map(this::aResponse).toList();
    }

    @Override
    public LibroResponse actualizar(Long id, LibroRequest request) {
        Libro libro = libroRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro con id " + id + " no encontrado"));

        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setIsbn(request.getIsbn());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setEstado(request.getEstado());

        return aResponse(libro);
    }

    @Override
    public void eliminar(Long id) {
        boolean eliminado = libroRepository.eliminar(id);
        if (!eliminado) {
            throw new RecursoNoEncontradoException("Libro con id " + id + " no encontrado");
        }
    }

    private LibroResponse aResponse(Libro libro) {
        return new LibroResponse(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getAnioPublicacion(),
                libro.getEstado());
    }
}
