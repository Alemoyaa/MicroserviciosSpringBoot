package com.utn.curso.respuestas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utn.curso.respuestas.entity.Respuesta;
import com.utn.curso.respuestas.repository.RespuestasRepository;

@Service
public class RespuestasServiceImpl  implements RespuestaService{

	@Autowired
	private RespuestasRepository repository;
	
	@Override
	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) { 
		return repository.saveAll(respuestas);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) { 
		return repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) { 
		return repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
	}
	
}
