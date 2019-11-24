package com.utn.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utn.main.entities.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

}
