package com.utn.curso.models.service;

import java.util.List;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utn.curso.commons.alumnos.entity.Alumno;
import com.utn.curso.commons.services.CommonServiceImplimentacion;
import com.utn.curso.models.repository.AlumnoRepository;

@Service
public class AlumnoServiceImplimentacion extends CommonServiceImplimentacion<Alumno, AlumnoRepository> implements AlumnoService{

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOApellido(String term) {
		return repository.findByNombreOApellido(term);
	} 
	
}
