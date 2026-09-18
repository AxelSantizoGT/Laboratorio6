package com.lab.cursos.service;

import com.lab.cursos.dto.CursoRequest;
import com.lab.cursos.dto.CursoResponse;
import com.lab.cursos.exception.RecursoDuplicadoException;
import com.lab.cursos.exception.RecursoNoEncontradoException;
import com.lab.cursos.model.Curso;
import com.lab.cursos.model.EstadoCurso;
import com.lab.cursos.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementacion del servicio de cursos. Contiene las reglas de negocio
 * y delega el almacenamiento al repositorio en memoria.
 */
@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public CursoResponse crear(CursoRequest request) {
        cursoRepository.buscarPorCodigo(request.getCodigo()).ifPresent(c -> {
            throw new RecursoDuplicadoException("Ya existe un curso registrado con el codigo " + request.getCodigo());
        });

        Curso curso = new Curso(
                null,
                request.getNombre(),
                request.getCodigo(),
                request.getCreditos(),
                request.getEstado());

        Curso guardado = cursoRepository.guardar(curso);
        return aResponse(guardado);
    }

    @Override
    public List<CursoResponse> consultarTodos(EstadoCurso estado) {
        return cursoRepository.buscarTodos().stream()
                .filter(c -> estado == null || c.getEstado() == estado)
                .map(this::aResponse)
                .toList();
    }

    @Override
    public CursoResponse consultarPorCodigo(String codigo) {
        Curso curso = cursoRepository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso con codigo " + codigo + " no encontrado"));
        return aResponse(curso);
    }

    @Override
    public CursoResponse actualizar(Long id, CursoRequest request) {
        Curso curso = cursoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso con id " + id + " no encontrado"));

        curso.setNombre(request.getNombre());
        curso.setCodigo(request.getCodigo());
        curso.setCreditos(request.getCreditos());
        curso.setEstado(request.getEstado());

        return aResponse(curso);
    }

    @Override
    public void eliminar(Long id) {
        boolean eliminado = cursoRepository.eliminar(id);
        if (!eliminado) {
            throw new RecursoNoEncontradoException("Curso con id " + id + " no encontrado");
        }
    }

    private CursoResponse aResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getNombre(),
                curso.getCodigo(),
                curso.getCreditos(),
                curso.getEstado());
    }
}
