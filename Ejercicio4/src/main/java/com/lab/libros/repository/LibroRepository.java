package com.lab.libros.repository;

import com.lab.libros.model.Libro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio en memoria para el almacenamiento de libros.
 * Utiliza una lista sincronizada como almacen de datos, ya que no
 * se requiere persistencia en base de datos para este laboratorio.
 */
@Repository
public class LibroRepository {

    private final List<Libro> libros = new ArrayList<>();
    private final AtomicLong secuenciaId = new AtomicLong(0);

    public Libro guardar(Libro libro) {
        libro.setId(secuenciaId.incrementAndGet());
        libros.add(libro);
        return libro;
    }

    public List<Libro> buscarTodos() {
        return new ArrayList<>(libros);
    }

    public Optional<Libro> buscarPorId(Long id) {
        return libros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        String textoBusqueda = titulo.toLowerCase();
        return libros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(textoBusqueda))
                .toList();
    }

    public Optional<Libro> buscarPorIsbn(String isbn) {
        return libros.stream()
                .filter(l -> l.getIsbn().equalsIgnoreCase(isbn))
                .findFirst();
    }

    public boolean eliminar(Long id) {
        return libros.removeIf(l -> l.getId().equals(id));
    }
}
