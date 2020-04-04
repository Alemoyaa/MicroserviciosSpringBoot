package com.utn.curso.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utn.curso.commons.examenes.entity.Asignatura;
import com.utn.curso.commons.examenes.entity.Examen;
import com.utn.curso.commons.services.CommonServiceImplimentacion;
import com.utn.curso.examenes.models.repository.AsignaturaRepository;
import com.utn.curso.examenes.models.repository.ExamenRepository;

@Service
public class ExamenServiceImpl extends CommonServiceImplimentacion<Examen, ExamenRepository> implements ExamenService{

	@Autowired
	private AsignaturaRepository aRepository;
	
	@Override
	@Transactional
	public List<Examen> findByNombre(String term) {
		return repository.findByNombre(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asignatura> findAllAsignaturas() { 
		return (List<Asignatura>) aRepository.findAll();
	}

}
