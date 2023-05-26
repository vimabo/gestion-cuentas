/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.banco.dto;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 *
 * @author bocanegravm
 */

public class MovimientosDto {

	private int id;

	@NotNull(message = "La fecha no puede ser Nula")
	private Date fecha;

	@NotNull(message = "El tipo movimiento no puede ser Nulo")
	private int tipoMovimiento;

	@NotNull(message = "El valor no puede ser Nulo")
	private BigDecimal valor;

	@NotNull(message = "La cuenta no puede ser Nula")
	private CuentasDto cuenta;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(int tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public CuentasDto getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentasDto cuenta) {
		this.cuenta = cuenta;
	}

}
