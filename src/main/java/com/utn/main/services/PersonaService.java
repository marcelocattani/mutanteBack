package com.utn.main.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.utn.main.dtos.PersonaDto;
import com.utn.main.entities.Adn;
import com.utn.main.entities.Persona;
import com.utn.main.repositories.PersonaRepository;

@Service
public class PersonaService {
	
	PersonaRepository pr;
	
	public PersonaService (PersonaRepository pr)  {
		this.pr = pr;
	}
	
	@Transactional
	public List<PersonaDto> getAll() throws Exception {
		ArrayList<PersonaDto> listaDtos = new ArrayList<PersonaDto>();
		List<Persona> entidades= pr.findAll();
		
		try {
			for (Persona entidad : entidades) {
				boolean esMutante = entidad.getAdn().getMutante() == 'T';
				
				PersonaDto dtoTemporal = new PersonaDto();
				
				dtoTemporal.setId(entidad.getId());
				dtoTemporal.setNombre(entidad.getNombre());
				dtoTemporal.setApellido(entidad.getApellido());
				
				dtoTemporal.setCadena(entidad.getAdn().getCadena());
				dtoTemporal.setMutante(esMutante);
				
				listaDtos.add(dtoTemporal);
				
			}
			
		} catch (Exception e) {
			System.err.println("PersonaService.getAll()"+e.getMessage());
		}
		
		return listaDtos;
		
	}
	
	@Transactional
	public PersonaDto getOne(int id) {
		
		PersonaDto salida = new PersonaDto();
		Optional<Persona> personaOptional = pr.findById(id);
		Persona persona; 
		
		try {
			persona = personaOptional.get();
			
			boolean esMutate = persona.getAdn().getMutante() == 'T';
			
			salida.setNombre(persona.getNombre());
			salida.setApellido(persona.getApellido());
			salida.setId(persona.getId());
			
			salida.setCadena(persona.getAdn().getCadena());
			salida.setMutante(esMutate);
			
		} catch (Exception e) {
			System.err.println("PersonaService.getOne()"+e.getMessage());
		}
		
		return salida;		
		
	}
	
	@Transactional
	public PersonaDto save(PersonaDto persona) throws Exception {
		char esMutante;
		Persona entidad = new Persona();
		
		entidad.setNombre(persona.getNombre());
		entidad.setApellido(persona.getApellido());
		
		if ( this.isMutant(persona.getCadena())) { //Si esta linea no lanza excepcion la cadena es valida
			esMutante = 'T';
			persona.setMutante(true);
		} else {
			esMutante = 'F';
			persona.setMutante(false);
		}
		
		
		entidad.setAdn(new Adn(persona.getCadena(),esMutante,entidad));
		
		try {
			entidad = pr.save(entidad);
			persona.setId(entidad.getId());
			return persona;
			
		} catch (Exception e) {
			System.err.println("Error en personaService.save()"+e.getMessage());
			return null;
		}
		
		
		
	}
	
	@Transactional
	public PersonaDto update(PersonaDto persona, int id) throws Exception{
		Optional<Persona> personaOptional = pr.findById(id);
		PersonaDto salida = new PersonaDto();
		Persona entidad;
		Adn adn;
				
		if (pr.existsById(id) ) {
			
			char esMutante;
			if ( this.isMutant(persona.getCadena())) { //Si esta linea no lanza excepcion la cadena es valida
				esMutante = 'T';
				persona.setMutante(true);
			} else {
				esMutante = 'F';
				persona.setMutante(false);
			}
			
			try {
				entidad = personaOptional.get();
				adn = entidad.getAdn();
				
				entidad.setNombre(persona.getNombre());
				entidad.setApellido(persona.getApellido());
				
				
				
				adn.setCadena(persona.getCadena());
				adn.setMutante(esMutante);
				
				entidad = pr.save(entidad);
				persona.setId(entidad.getId());
				
				
				
			} catch (Exception e) {
				System.err.println("Error en personaService.update()"+e.getMessage());
				return null;
			}
		}
		return persona;
	}
	
	@Transactional
	public boolean delete(int id) {
		if(pr.existsById(id)) {
			try {
				pr.deleteById(id);
				return true;
			} catch (Exception e) {
				System.err.println("Error en PersonaService.delete()"+e.getMessage());
			}
			
		}
		
		return false;
	}
	

    /*--------Todo metodos sobre Mutantes------------*/
	
	
	
    public boolean isMutant(String[] adn) throws Exception {
        byte coincidencias = 0;
        if (validarDatos(adn)) {
            byte[][] arreglo = hacerMatriz(adn);

            coincidencias += validarHorizontal(arreglo);
            if (coincidencias > 1) {
                return true; //Valida la cantidad de secuencias para evitar realizar calculos demas
            }
            coincidencias += validarVertical(arreglo);
            if (coincidencias > 1) {
                return true;
            }

            coincidencias += validarOblicuaNegativa(arreglo);
            if (coincidencias > 1) {
                return true;
            }

            coincidencias += validarOblicuaPositiva(arreglo);
            return coincidencias > 1; //si llega al final con solo una secuencia de letras el humano esta sano
        } else {
            throw new Exception();
        }

    }

    private byte validarHorizontal(byte[][] arreglo) {
        byte contador = 0;

        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo[0].length - 3; j++) {
                if (arreglo[i][j] == arreglo[i][j + 1]
                        && arreglo[i][j] == arreglo[i][j + 2]
                        && arreglo[i][j] == arreglo[i][j + 3]) {
                    contador++;
                }
            }
        }

        return contador;
    }

    private byte validarVertical(byte[][] arreglo) {
        byte contador = 0;

        for (int i = 0; i < arreglo.length - 3; i++) {
            for (int j = 0; j < arreglo[0].length; j++) {
                if (arreglo[i][j] == arreglo[i + 1][j]
                        && arreglo[i][j] == arreglo[i + 2][j]
                        && arreglo[i][j] == arreglo[i + 3][j]) {
                    contador++;
                }
            }
        }

        return contador;
    }

    private byte validarOblicuaNegativa(byte[][] arreglo) {
        byte contador = 0;

        for (int i = 0; i < arreglo.length - 3; i++) {
            for (int j = 0; j < arreglo[0].length - 3; j++) {
                if (arreglo[i][j] == arreglo[i + 1][j + 1]
                        && arreglo[i][j] == arreglo[i + 2][j + 2]
                        && arreglo[i][j] == arreglo[i + 3][j + 3]) {
                    contador++;
                }
            }
        }
        return contador;
    }

    private byte validarOblicuaPositiva(byte[][] arreglo) {
        byte contador = 0;

        for (int i = 0; i < arreglo.length - 3; i++) {
            for (int j = 3; j < arreglo[0].length; j++) {
                if (arreglo[i][j] == arreglo[i + 1][j - 1]
                        && arreglo[i][j] == arreglo[i + 2][j - 2]
                        && arreglo[i][j] == arreglo[i + 3][j - 3]) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /*
     * Genera una matriz de tipo byte ya que es mas liviana que char
     *
     * @param arreglo adn
     * @return una matriz con valores de 0-4
     */
    private byte[][] hacerMatriz(String[] arreglo) {

        byte[][] salida = new byte[arreglo.length][arreglo.length];
        byte valor = -1;
        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo[i].length(); j++) {

                switch (arreglo[i].toUpperCase().charAt(j)) {
                    case 'A':
                        valor = 0;
                        break;
                    case 'T':
                        valor = 1;
                        break;
                    case 'C':
                        valor = 2;
                        break;
                    case 'G':
                        valor = 3;
                        break;
                    default:
                        System.err.println("Warning: La cadena no llego con formato valido");
                }

                salida[i][j] = valor;

            }

        }

        return salida;
    }

    /*
     * Valida que se usen solo letras A, T, C, G. Que el tamanio del arreglo sea
     * igual al largo de cada elemento. y que no sea menor a tres.
     *
     * @param arreglo de Adn
     * @return true si la secuencia es correcta para evaluar, Exception si no es valida
     */
    private boolean validarDatos(String[] arreglo) throws Exception {

        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < arreglo[i].length(); j++) {
                char letra = arreglo[i].toUpperCase().charAt(j);
                if (!(letra == 'A' || letra == 'T' || letra == 'C' || letra == 'G')) {                    
                    throw new Exception("AdnFormatInvalid");

                }
            }
        }

        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].length() != arreglo.length) {
                throw new Exception("AdnFormatInvalid");
            }
        }

        if (arreglo[0].length() > 3) {
            return true;
        }

        throw new Exception("AdnFormatInvalid");
    }

	
	
	
	
	
	
}
