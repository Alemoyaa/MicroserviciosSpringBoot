package com.utn.curso.examenes.controller;
 
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.utn.curso.commons.controller.CommonController;
import com.utn.curso.commons.examenes.entity.Examen;
import com.utn.curso.examenes.services.ExamenService;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService>{

	@PutMapping("/{id}")
	public ResponseEntity<?> editar (@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Examen> o = service.findById(id);
		
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Examen examenDB = o.get();
		examenDB.setNombre(examen.getNombre());
		
		examenDB.getPreguntas()
		.stream() //Convertimos a stream
		.filter(pdb -> !examen.getPreguntas().contains(pdb))//Contains es para preguntar si un elemento existe en la lista
		//Agregamos todas las preguntas que queremos borrar al nuevo stream
		.forEach(examenDB::removerPregunta); //Borramos todas las preguntas del arreglo que vamos a guardar
	 	
		examenDB.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDB));
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombre(term));
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
}
