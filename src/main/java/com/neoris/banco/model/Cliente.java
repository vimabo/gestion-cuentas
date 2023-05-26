/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.banco.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author bocanegravm
 */

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "persona_id")
public class Cliente extends Persona {

	@Column(nullable = false, length = 20)
	private String contraseña;

	private Boolean estado = false;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Cuenta.class, mappedBy = "cliente")
	private List<Cuenta> cuentas;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Movimientos.class)
	private Movimientos movimientos;
	
	

	public Cliente(){
		super();
	}

	public Cliente(String nombre, String genero, int edad, String identificacion, String direccion, String telefono,
			String contraseña, Boolean estado) {
		super(nombre, genero, edad, identificacion, direccion, telefono);
		this.contraseña = contraseña;
		this.estado = estado;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public Movimientos getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Movimientos movimientos) {
		this.movimientos = movimientos;
	}

}
