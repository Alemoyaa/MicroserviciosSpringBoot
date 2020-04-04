package com.utn.curso.models.service;

import java.util.List;
 
import com.utn.curso.commons.alumnos.entity.Alumno;
import com.utn.curso.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno>{
 
	public List<Alumno> findByNombreOApellido(String term);
	
}
