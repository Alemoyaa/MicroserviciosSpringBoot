package com.utn.curso.cursos.models.repository;

import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.PagingAndSortingRepository;

import com.utn.curso.cursos.models.entity.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{
	
	@Query("select c from Curso c join fetch c.alumnos a where a.id=?1")
	public Curso findCursoByAlumnoId(Long id);

}
