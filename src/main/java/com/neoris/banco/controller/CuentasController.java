package com.neoris.banco.controller;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.neoris.banco.dto.CuentasDto;
import com.neoris.banco.services.BancoService;
import com.neoris.banco.util.InvalidDataException;

@RestController
@RequestMapping("cuentas")
public class CuentasController {

	@Autowired
	private BancoService service;

	private static final Logger logger = LoggerFactory.getLogger(CuentasController.class);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@Valid @RequestBody CuentasDto cuentaDto, BindingResult result) {
		logger.debug("Creando cuenta con data {}", cuentaDto);
		if (result.hasErrors()) {
			throw new InvalidDataException(result);
		}
		cuentaDto = service.crearCuenta(cuentaDto, result);
		return "Cuenta creada con Id: " + cuentaDto.getId();
	}

	@PutMapping("/{idCuenta}")
	@ResponseStatus(HttpStatus.OK)
	public CuentasDto update(@PathVariable("idCuenta") int idCuenta, @Valid @RequestBody CuentasDto cuenta,
			BindingResult result) {
		logger.debug("Actualizando cuenta con data {}", cuenta);
		if (result.hasErrors()) {
			throw new InvalidDataException(result);
		}
		cuenta = service.updateCuenta(idCuenta, cuenta, result);
		return cuenta;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CuentasDto> getCuentas() {
		logger.debug("Obteniendo todas las cuentas:");
		return service.getCuentas();
	}

	@GetMapping("/{idCuenta}")
	@ResponseStatus(HttpStatus.OK)
	public CuentasDto getCuenta(@PathVariable("idCuenta") int idCuenta) {
		logger.debug("Obteniendo cuenta con data {}", idCuenta);
		return service.getCuenta(idCuenta);

	}

	@DeleteMapping(value = "/{idCuenta}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteCuenta(@PathVariable int idCuenta) {
		logger.debug("Borrando cuenta con data {}", idCuenta);
		service.deleteCuenta(idCuenta);
		return "Cuenta eliminada";
	}
	
	@GetMapping(value = "/getCuentasByCliente/{clienteId}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<CuentasDto> getCuentasByCliente(@RequestParam("fechaInicial") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaInicial,
			@RequestParam("fechaFinal") @DateTimeFormat(pattern="yyyy-MM-dd") 
	Date fechaFinal, @PathVariable("clienteId") int clienteId) {
		return service.listCuentasByCliente(clienteId, fechaInicial, fechaFinal);
	}

}
