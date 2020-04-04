package com.utn.curso.cursos.models.services;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.utn.curso.commons.services.CommonServiceImplimentacion;
import com.utn.curso.cursos.models.clients.RespuestaFeignClients;
import com.utn.curso.cursos.models.entity.Curso;
import com.utn.curso.cursos.models.repository.CursoRepository;

@Service
public class CursoServiceImplementacion extends CommonServiceImplimentacion<Curso, CursoRepository> implements CursoService{

	@Autowired
	private RespuestaFeignClients client;
	
	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlumnoId(Long id) {
		return repository.findCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {		 
		return client.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
	}  
}
