package com.utn.main.entities;


import javax.persistence.*;

@Entity
public class Persona {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	int id;
	
	@Column (name =  "persona_nombre")
	private String nombre;
		
	@Column (name = "persona_apellido")
	private String apellido;
	
	
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn (name =  "persona_fk_adn")
	private Adn adn;

	public Persona() {
	}

	public Persona(String nombre, String apellido, Adn adn) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.adn = adn;
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

	public Adn getAdn() {
		return adn;
	}

	public void setAdn(Adn adn) {
		this.adn = adn;
	}
	
}
