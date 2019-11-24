package com.utn.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utn.main.entities.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	
	@Query (value = "SELECT COUNT(*) FROM adn WHERE adn_es_mutante = :tipo", nativeQuery = true)
	public long contarPorTipo(@Param ("tipo") char tipo);
}
