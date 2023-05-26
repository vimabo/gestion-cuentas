package com.neoris.banco.controller;

import java.util.List;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.neoris.banco.dto.MovimientosDto;
import com.neoris.banco.services.BancoService;
import com.neoris.banco.util.InvalidDataException;

@RestController
@RequestMapping("movimientos")
public class MovimientosController {

	@Autowired
	private BancoService service;

	private static final Logger logger = LoggerFactory.getLogger(MovimientosController.class);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@Valid @RequestBody MovimientosDto movimientosDto, BindingResult result) {
		logger.debug("Creando un movimiento con data {}", movimientosDto);
		if (result.hasErrors()) {
			throw new InvalidDataException(result);
		}
		movimientosDto = service.crearMovimiento(movimientosDto, result);
		if (movimientosDto.getTipoMovimiento() == 1) {
			return "Movimiento Credito creado con Id: " + movimientosDto.getId();
		} else {
			return "Movimiento Debito  creado con Id: " + movimientosDto.getId();
		}
	}

	@DeleteMapping(value = "/{idMovimiento}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteMovimiento(@PathVariable int idMovimiento) {
		logger.debug("Borrando Movimiento con data {}", idMovimiento);
		service.deleteMovimiento(idMovimiento);
		return "Movimiento eliminado";
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<MovimientosDto> getMovimientos() {
		logger.debug("Obteniendo todos los movimientos:");
		return service.getMovimientos();
	}

	@GetMapping("/{idMovimiento}")
	@ResponseStatus(HttpStatus.OK)
	public MovimientosDto getMovimiento(@PathVariable("idMovimiento") int idMovimiento) {
		logger.debug("Obteniendo movimiento con data {}", idMovimiento);
		return service.getMovimiento(idMovimiento);

	}

}
