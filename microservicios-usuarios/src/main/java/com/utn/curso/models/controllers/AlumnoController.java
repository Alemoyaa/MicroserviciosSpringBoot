package com.utn.curso.models.controllers;

import java.io.IOException;
import java.util.Optional;
 
import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import com.utn.curso.commons.alumnos.entity.Alumno;
import com.utn.curso.commons.controller.CommonController;
import com.utn.curso.models.service.AlumnoService;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService>{ 

	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
		Optional<Alumno> Alumno = service.findById(id);
		
		if(Alumno.isEmpty() || Alumno.get().getFoto() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource imagen = new ByteArrayResource(Alumno.get().getFoto());
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> put(@Valid @RequestBody Alumno alum, BindingResult result, @PathVariable Long id) {
		
		if(result.hasErrors()) {
			return validar(result);
		}
		
		Optional<Alumno> Alumno = service.findById(id);
		
		if(Alumno.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Alumno aluDB = Alumno.get();
		
		aluDB.setApellido(alum.getApellido());
		aluDB.setNombre(alum.getNombre());
		aluDB.setEmail(alum.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(aluDB));
	} 
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombreOApellido(term));
	}
 
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
		if(!archivo.isEmpty()) { //Asignamos y resivimos la foto
			alumno.setFoto(archivo.getBytes());
		}
		return super.crear(alumno, result);
	}
	
	@PutMapping("/editar-con-foto/{id}")
	@Transactional
	public ResponseEntity<?> editarConFoto(@Valid Alumno alum, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException {
		
		if(result.hasErrors()) {
			return validar(result);
		}
		
		Optional<Alumno> Alumno = service.findById(id);
		
		if(Alumno.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Alumno aluDB = Alumno.get();
		
		aluDB.setApellido(alum.getApellido());
		aluDB.setNombre(alum.getNombre());
		aluDB.setEmail(alum.getEmail());
		
		if(!archivo.isEmpty()) { //Asignamos y resivimos la foto
			aluDB.setFoto(archivo.getBytes());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(aluDB));
	} 
	
}
