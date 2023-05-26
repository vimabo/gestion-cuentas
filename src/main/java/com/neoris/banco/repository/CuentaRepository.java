/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.banco.repository;

import com.neoris.banco.model.Cliente;
import com.neoris.banco.model.Cuenta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bocanegravm
 */
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

	Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

	Optional<Cuenta> findById(int id);

	@Query("SELECT c FROM Cuenta c WHERE c.id != ?1 AND c.numeroCuenta = ?2")
	Optional<Cuenta> findByIdAndNumeroCuenta(int id, String numeroCuenta);
	
	@Query("SELECT c FROM Cuenta c JOIN c.cliente cli WHERE cli = ?1")
	List<Cuenta> findByClienteAndFechas(Cliente cliente);

}
