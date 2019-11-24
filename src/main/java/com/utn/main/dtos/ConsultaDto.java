package com.utn.main.dtos;

public class ConsultaDto {
	
	private String[] cadena;
	private boolean mutante;
	
	public ConsultaDto() {	
	}

	public ConsultaDto(String[] cadena, boolean mutante) {
		this.cadena = cadena;
		this.mutante = mutante;
	}

	public String[] getCadena() {
		return cadena;
	}

	public void setCadena(String[] cadena) {
		this.cadena = cadena;
	}
	
	public boolean getMutante() {
		return mutante;
	}

	public void setMutante(boolean mutante) {
		this.mutante = mutante;
	}
	
		
	
}
