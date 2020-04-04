package com.utn.curso.cursos.models.services;

import com.utn.curso.commons.services.CommonService;
import com.utn.curso.cursos.models.entity.Curso;

public interface CursoService extends CommonService<Curso>{ 
	public Curso findCursoByAlumnoId(Long id);
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);
}
