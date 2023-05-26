/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.banco.repository;

import com.neoris.banco.model.Persona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bocanegravm
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

	Optional<Persona> findByIdentificacion(String identificacion);

	@Query("SELECT p FROM Persona p WHERE p.id != ?1 AND p.identificacion = ?2")
	Optional<Persona> findByIdAndIdentificacion(int id, String identificacion);

}
