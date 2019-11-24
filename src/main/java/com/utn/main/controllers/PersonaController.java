package com.utn.main.controllers;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.utn.main.dtos.PersonaDto;
import com.utn.main.services.PersonaService;

@RestController
@CrossOrigin  (origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping (path = "api/v1/mutantes")
public class PersonaController {
	
	private PersonaService ps; 
	
	public PersonaController(PersonaService ps) {
		this.ps = ps;
	}
	
	@Transactional
	@GetMapping ("/")
	public ResponseEntity getAll(){
		try {			
			return ResponseEntity.status(HttpStatus.OK).body(ps.getAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Ha ocurrido un error\"}");
		}
	}
	
	@Transactional
	@GetMapping ("/{id}")
	public ResponseEntity getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(ps.getOne(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Ha ocurrido un error\"}");
		}
	}
	
	@Transactional
	@PostMapping ("/")
	public ResponseEntity save(@RequestBody PersonaDto persona) {
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(ps.save(persona));
		} catch (Exception e) {
			
			if (e.getMessage().equals("AdnFormatInvalid") ) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\":\"La Cadena de Adn no es Valida\"}");
			} 
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Ha ocurrido un error\"}");
		}
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody PersonaDto persona, @PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(ps.update(persona, id));
		} catch (Exception e) {
			
			if (e.getMessage().equals("AdnFormatInvalid") ) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\":\"La Cadena de Adn no es Valida\"}");
			} 
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Ha ocurrido un error\"}");
		}
	}
	
	@Transactional
	@DeleteMapping ("{id}")
	public ResponseEntity delete(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(ps.delete(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Ha ocurrido un error\"}");
		}
	}
	
	@Transactional
	@PostMapping ("/mutant/")
	public ResponseEntity isMutant(@RequestBody PersonaDto persona) {
		try {
			
			return ResponseEntity.status(HttpStatus.OK).body(ps.isMutant(persona.getCadena()));
			
		} catch (Exception e) {			
			if (e.getMessage().equals("AdnFormatInvalid") ) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\":\"La Cadena de Adn no es Valida\"}");
			} 
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Ha ocurrido un error\"}");		
		}
	}
	
}
