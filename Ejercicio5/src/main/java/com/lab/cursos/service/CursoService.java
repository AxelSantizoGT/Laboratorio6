package com.lab.cursos.service;

import com.lab.cursos.dto.CursoRequest;
import com.lab.cursos.dto.CursoResponse;
import com.lab.cursos.model.EstadoCurso;

import java.util.List;

/**
 * Contrato de servicio para las operaciones de negocio sobre cursos.
 */
public interface CursoService {

    CursoResponse crear(CursoRequest request);

    List<CursoResponse> consultarTodos(EstadoCurso estado);

    CursoResponse consultarPorCodigo(String codigo);

    CursoResponse actualizar(Long id, CursoRequest request);

    void eliminar(Long id);
}
