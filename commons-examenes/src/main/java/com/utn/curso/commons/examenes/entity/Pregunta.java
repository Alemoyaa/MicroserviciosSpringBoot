package com.utn.curso.commons.examenes.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; 

@Entity
@Table(name = "preguntas")
public class Pregunta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String texto;

	@JsonIgnoreProperties(value = {"preguntas"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="examen_id")
	private Examen examen;
	
	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Pregunta)) {
			return false;
		}
		Pregunta preg = (Pregunta) obj;
		
		return this.id != null && this.id.equals(preg.getId());
	} 
}
