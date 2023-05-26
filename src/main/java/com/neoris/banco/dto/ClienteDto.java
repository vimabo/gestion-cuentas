/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.banco.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author bocanegravm
 */

public class ClienteDto {

	private int id;

	@NotNull(message = "El nombre no puede ser Nulo")
	@Size(min = 2, message = "El nombre debe tener al menos dos caracteres")
	private String nombre;

	private String genero;

	@NotNull(message = "La edad no puede ser Nulo")
	private int edad;

	@NotNull(message = "La identificacion no puede ser Nula")
	@Size(min = 2, message = "La identificacion debe tener al menos seis caracteres")
	private String identificacion;

	private String direccion;

	private String telefono;
	
	@NotNull(message = "La contraseña no puede ser Nula")
	@Size(min = 2, message = "La contraseña debe tener al menos cuatro caracteres")
	private String contraseña;

	private Boolean estado = true;

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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

}
