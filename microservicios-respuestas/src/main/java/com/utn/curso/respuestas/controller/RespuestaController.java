package com.utn.curso.respuestas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.utn.curso.respuestas.entity.Respuesta;
import com.utn.curso.respuestas.services.RespuestaService;

@RestController
public class RespuestaController{

	@Autowired
	private RespuestaService service;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAll(respuestas));
	}
	
	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespuestaPorAlummnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
		Iterable<Respuesta> respuestas = service.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId){
		Iterable<Long> examenesIds = service.findExamenesIdsConRespuestasByAlumno(alumnoId);
		return ResponseEntity.ok(examenesIds);
	}
}
