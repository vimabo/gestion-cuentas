/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoris.banco.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author bocanegravm
 */

public class CuentasDto {

	private int id;

	@NotNull(message = "El numero Cuenta no puede ser Nulo")
	@Size(min = 6, message = "El numero Cuenta debe tener al menos seis caracteres")
	@Pattern(regexp = "^[a-zA-Z0-9]{6,12}$", message = "numero Cuenta debe tener de 6 a 12 caracteres sin caracteres especiales")
	private String numeroCuenta;

	private BigDecimal saldoInicial = BigDecimal.ZERO;

	@NotNull(message = "El tipo Cuenta no puede ser Nulo")
	private int tipoCuenta;

	private Boolean estado = true;

	@NotNull(message = "El cliente no puede ser Nulo")
	private ClienteDto cliente;

	private List<MovimientosDto> movimientos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public int getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public List<MovimientosDto> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<MovimientosDto> movimientos) {
		this.movimientos = movimientos;
	}

}
