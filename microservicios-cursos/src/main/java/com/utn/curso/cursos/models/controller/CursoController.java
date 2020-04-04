package com.utn.curso.cursos.models.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.utn.curso.commons.alumnos.entity.Alumno;
import com.utn.curso.commons.controller.CommonController;
import com.utn.curso.commons.examenes.entity.Examen;
import com.utn.curso.cursos.models.entity.Curso;
import com.utn.curso.cursos.models.services.CursoService;

@RestController
public class CursoController extends CommonController<Curso, CursoService>{

	@Value("${config.balanceador.test}")
	private String balanceadorTest;
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return validar(result);
		}
		Optional<Curso> o = this.service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Curso dbCurso = o.get();
		
		dbCurso.setNombre(curso.getNombre());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
	
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@Valid @RequestBody List<Alumno> alumnos,BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return validar(result);
		}
		Optional<Curso> o = this.service.findById(id);
		
		System.out.println("Curso: "+o.get().getNombre());
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		System.out.println("Llegue joya");
		Curso dbCurso = o.get();
		alumnos.forEach(a -> {
			dbCurso.addAlumno(a);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
	
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return validar(result);
		}
		
		Optional<Curso> o = this.service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = o.get();
		
		dbCurso.removeAlumno(alumno);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
		Curso curso = service.findCursoByAlumnoId(id);
		
		if(curso != null) {
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
			
			List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
				if(examenesIds.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			
			curso.setExamenes(examenes);
		}
		
		return ResponseEntity.ok(curso);
	}
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@Valid @RequestBody List<Examen> Examenes, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return validar(result);
		}
		Optional<Curso> o = this.service.findById(id); 
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		} 
		Curso dbCurso = o.get();
		Examenes.forEach(e -> {
			dbCurso.addExamen(e);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
	
	@PutMapping("/{id}/eliminar-examenes")
	public ResponseEntity<?> eliminarExamenes(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return validar(result);
		}
		Optional<Curso> o = this.service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = o.get();
		
		dbCurso.removeExamen(examen);

		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
}
