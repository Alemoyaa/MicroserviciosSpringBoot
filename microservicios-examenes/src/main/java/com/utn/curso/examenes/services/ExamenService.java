package com.utn.curso.examenes.services;

import java.util.List;

import com.utn.curso.commons.examenes.entity.Asignatura;
import com.utn.curso.commons.examenes.entity.Examen;
import com.utn.curso.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen>{
	public List<Examen> findByNombre(String term);
	
	public List<Asignatura> findAllAsignaturas();  
}
