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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.neoris.banco.dto.ClienteDto;
import com.neoris.banco.services.BancoService;
import com.neoris.banco.util.InvalidDataException;

@RestController
@RequestMapping("clientes")
public class ClienteController {

	@Autowired
	private BancoService service;

	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@Valid @RequestBody ClienteDto clienteDto, BindingResult result) {
		logger.debug("Creando cliente con data {}", clienteDto);
		if (result.hasErrors()) {
			throw new InvalidDataException(result);
		}
		clienteDto = service.crearCliente(clienteDto);
		return "Cliente creado con Id: " + clienteDto.getId();
	}

	@PutMapping("/{idCliente}")
	@ResponseStatus(HttpStatus.OK)
	public ClienteDto update(@PathVariable("idCliente") int idCliente, @Valid @RequestBody ClienteDto clienteDto,
			BindingResult result) {
		logger.debug("Actualizando cliente con data {}", clienteDto);
		if (result.hasErrors()) {
			throw new InvalidDataException(result);
		}
		clienteDto = service.updateCliente(idCliente, clienteDto, result);
		return clienteDto;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ClienteDto> getClientes() {
		logger.debug("Obteniendo todos los clientes:");
		return service.getClientes();
	}

	@GetMapping("/{idCliente}")
	@ResponseStatus(HttpStatus.OK)
	public ClienteDto getCliente(@PathVariable("idCliente") int idCliente) {
		logger.debug("Obteniendo cliente con data {}", idCliente);
		return service.getCliente(idCliente);

	}

	@DeleteMapping(value = "/{idCliente}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteCliente(@PathVariable int idCliente) {

		logger.debug("Borrando cliente con data {}", idCliente);
		service.deleteCliente(idCliente);
		return "Cliente eliminado";
	}

}
