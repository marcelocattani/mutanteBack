package com.utn.main.dtos;

import java.io.Serializable;

public class PersonaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	int id;
	String nombre;
	String apellido; 
	boolean mutante;
	String[] cadena;
	
	public PersonaDto() {
	}

	public PersonaDto(String nombre, String apellido, boolean mutante, String[] adn) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.mutante = mutante;
		this.cadena = adn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public boolean getMutante() {
		return mutante;
	}

	public void setMutante(boolean mutante) {
		this.mutante = mutante;
	}

	public String[] getCadena() {
		return cadena;
	}

	public void setCadena(String[] cadena) {
		this.cadena = cadena;
	}

	
	
}
