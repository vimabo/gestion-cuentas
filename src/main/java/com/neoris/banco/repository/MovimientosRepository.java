/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.banco.repository;

import com.neoris.banco.model.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bocanegravm
 */
@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Integer> {


}
