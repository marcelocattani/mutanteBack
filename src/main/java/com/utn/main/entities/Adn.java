package com.utn.main.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;



@Entity
public class Adn {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (name = "Adn_cadena")
	private String[] cadena;
	
	
	@Column (name =  "Adn_esMutante")
	private char mutante;
	
	@OneToOne (mappedBy = "adn")
	@JoinColumn 
	private Persona persona;

	public Adn() {
	}

	public Adn(String[] cadena, char mutante, Persona persona) {	
		this.cadena = cadena;
		this.mutante = mutante;
		this.persona = persona;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getCadena() {
		return cadena;
	}

	public void setCadena(String[] cadena) {
		this.cadena = cadena;
	}

	public char getMutante() {
		return mutante;
	}

	public void setMutante(char mutante) {
		this.mutante = mutante;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	} 
	
}
