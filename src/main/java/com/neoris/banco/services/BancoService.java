package com.neoris.banco.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.neoris.banco.dto.ClienteDto;
import com.neoris.banco.dto.CuentasDto;
import com.neoris.banco.dto.MovimientosDto;
import com.neoris.banco.model.Cliente;
import com.neoris.banco.model.Cuenta;
import com.neoris.banco.model.Movimientos;
import com.neoris.banco.model.Persona;
import com.neoris.banco.repository.ClienteRepository;
import com.neoris.banco.repository.CuentaRepository;
import com.neoris.banco.repository.MovimientosRepository;
import com.neoris.banco.repository.PersonaRepository;
import com.neoris.banco.util.InvalidDataException;

@Service
public class BancoService {

	@Autowired
	private ClienteRepository repoCliente;

	@Autowired
	private PersonaRepository repoPersona;

	@Autowired
	private CuentaRepository repoCuenta;

	@Autowired
	private MovimientosRepository repoMovimientos;

	// Función que registra un cliente
	public ClienteDto crearCliente(ClienteDto nuevoCliente) {

		// Buscamos si hay una persona con la identificacion ingresada
		Optional<Persona> existe = repoPersona.findByIdentificacion(nuevoCliente.getIdentificacion());
		if (existe.isPresent()) {
			throw new IllegalArgumentException("Ya existe un cliente con ese numero de identificación.");
		} else {
			Cliente nuevo = new Cliente(nuevoCliente.getNombre(), nuevoCliente.getGenero(), nuevoCliente.getEdad(),
					nuevoCliente.getIdentificacion(), nuevoCliente.getDireccion(), nuevoCliente.getTelefono(),
					nuevoCliente.getContraseña(), nuevoCliente.getEstado());
			nuevo = repoCliente.save(nuevo);
			return returnClienteDto(nuevo);
		}

	}

	// Función que actualiza un cliente
	public ClienteDto updateCliente(Integer personaId, ClienteDto clienteDto, BindingResult result) {

		// Buscamos el cliente a actualizar
		Optional<Cliente> existe = repoCliente.findById(personaId);

		if (existe.isPresent()) {
			Cliente update = existe.get();
			// Buscamos si hay una persona con la identificacion ingresada
			Optional<Persona> ex = repoPersona.findByIdAndIdentificacion(personaId, clienteDto.getIdentificacion());
			if (ex.isPresent()) {
				result.rejectValue("identificacion", "Unique",
						"Ya existe un cliente con ese numero de identificación.");
				throw new InvalidDataException(result);
			} else {
				update.setNombre(clienteDto.getNombre());
				update.setGenero(clienteDto.getGenero());
				update.setEdad(clienteDto.getEdad());
				update.setIdentificacion(clienteDto.getIdentificacion());
				update.setDireccion(clienteDto.getDireccion());
				update.setTelefono(clienteDto.getTelefono());
				update.setContraseña(clienteDto.getContraseña());
				update.setEstado(clienteDto.getEstado());
				repoCliente.save(update);
				return returnClienteDto(update);
			}
		} else {
			result.rejectValue("id", "Noexiste", "No existe el cliente con el id ingresado.");
			throw new InvalidDataException(result);
		}

	}

	// Función que retorna un cliente
	public ClienteDto getCliente(int idCliente) {
		Optional<Cliente> existe = repoCliente.findById(idCliente);

		if (existe.isPresent()) {
			return returnClienteDto(existe.get());
		} else {
			throw new IllegalArgumentException("No existe el cliente con el id ingresado.");
		}
	}

	// Función que elimina un cliente
	public void deleteCliente(int idCliente) {
		Optional<Cliente> existe = repoCliente.findById(idCliente);

		if (existe.isPresent()) {
			repoCliente.delete(existe.get());
		} else {
			throw new IllegalArgumentException("No existe el cliente con el id ingresado.");
		}
	}

	// Función que retorna la lista de clientes
	public List<ClienteDto> getClientes() {
		List<Cliente> list = repoCliente.findAll();
		if (list.isEmpty()) {
			throw new IllegalArgumentException("No existen clientes en BD.");
		}
		return returnListaDto(list);
	}

	public List<ClienteDto> returnListaDto(List<Cliente> lista) {

		List<ClienteDto> clientes = new ArrayList<ClienteDto>();
		lista.stream().forEach(cliente -> {
			ClienteDto dto = new ClienteDto();
			dto.setId(cliente.getId());
			dto.setNombre(cliente.getNombre());
			dto.setGenero(cliente.getGenero());
			dto.setEdad(cliente.getEdad());
			dto.setIdentificacion(cliente.getIdentificacion());
			dto.setDireccion(cliente.getDireccion());
			dto.setTelefono(cliente.getTelefono());
			dto.setEstado(cliente.getEstado());
			clientes.add(dto);
		});

		return clientes;
	}

	public ClienteDto returnClienteDto(Cliente cliente) {

		ClienteDto dto = new ClienteDto();
		dto.setId(cliente.getId());
		dto.setNombre(cliente.getNombre());
		dto.setGenero(cliente.getGenero());
		dto.setEdad(cliente.getEdad());
		dto.setIdentificacion(cliente.getIdentificacion());
		dto.setDireccion(cliente.getDireccion());
		dto.setTelefono(cliente.getTelefono());
		dto.setEstado(cliente.getEstado());
		return dto;
	}

	public CuentasDto returnCuentaDto(Cuenta cuenta) {

		CuentasDto dto = new CuentasDto();
		dto.setId(cuenta.getId());
		dto.setNumeroCuenta(cuenta.getNumeroCuenta());
		dto.setTipoCuenta(cuenta.getTipoCuenta());
		dto.setEstado(cuenta.getEstado());
		dto.setSaldoInicial(cuenta.getSaldoInicial());
		dto.setCliente(returnClienteDto(cuenta.getCliente()));
		if (cuenta.getMovimientos() != null)
			dto.setMovimientos(returnListMovimientosDto(cuenta.getMovimientos()));
		return dto;
	}

	public MovimientosDto returnMovimientosDto(Movimientos movimiento) {

		MovimientosDto dto = new MovimientosDto();
		dto.setId(movimiento.getId());
		dto.setTipoMovimiento(movimiento.getTipoMovimiento());
		dto.setValor(movimiento.getValor());
		dto.setFecha(movimiento.getFecha());
		dto.setCuenta(returnCuentaDto(movimiento.getCuenta()));
		return dto;
	}

	public List<MovimientosDto> returnListMovimientosDto(List<Movimientos> movimientos) {
		List<MovimientosDto> list = new ArrayList<>();

		movimientos.stream().forEach(movimiento -> {
			MovimientosDto dto = new MovimientosDto();
			dto.setId(movimiento.getId());
			dto.setTipoMovimiento(movimiento.getTipoMovimiento());
			dto.setValor(movimiento.getValor());
			dto.setFecha(movimiento.getFecha());
			list.add(dto);
		});
		return list;
	}

	// Función que registra una cuenta
	public CuentasDto crearCuenta(CuentasDto cuenta, BindingResult result) {

		// Buscamos si hay una cuenta con el mismo numero
		Optional<Cuenta> existe = repoCuenta.findByNumeroCuenta(cuenta.getNumeroCuenta());

		if (existe.isPresent()) {
			result.rejectValue("numeroCuenta", "Unique", "Ya existe una cuenta con el mismo numero.");
			throw new InvalidDataException(result);
		} else {
			// Buscamos si existe la persona
			Optional<Cliente> exisCli = repoCliente.findById(cuenta.getCliente().getId());
			if (exisCli.isPresent()) {
				if (cuenta.getTipoCuenta() != 1 && cuenta.getTipoCuenta() != 2) {
					result.rejectValue("tipoCuenta", "Matches", "El tipo de cuenta debe ser: 1-Ahorros o 2-Corriente.");
					throw new InvalidDataException(result);
				} else {
					Cuenta nueva = new Cuenta(cuenta.getNumeroCuenta(), cuenta.getTipoCuenta(),
							cuenta.getSaldoInicial(), exisCli.get());
					repoCuenta.save(nueva);
					return returnCuentaDto(nueva);
				}

			} else {
				result.rejectValue("id", "Noexiste", "No existe el cliente con el id ingresado.");
				throw new InvalidDataException(result);
			}

		}

	}

	// Función que actualiza una cuenta
	public CuentasDto updateCuenta(Integer cuentaId, CuentasDto cuentaDto, BindingResult result) {

		// Buscamos la cuenta a actualizar
		Optional<Cuenta> existe = repoCuenta.findById(cuentaId);

		if (existe.isPresent()) {
			Cuenta update = existe.get();
			// Buscamos si hay una cuenta con el mismo numero
			Optional<Cuenta> exiNumero = repoCuenta.findByIdAndNumeroCuenta(cuentaId, cuentaDto.getNumeroCuenta());

			if (exiNumero.isPresent()) {
				result.rejectValue("numeroCuenta", "Unique", "Ya existe una cuenta con el mismo numero.");
				throw new InvalidDataException(result);
			} else {
				// Buscamos si existe la persona
				Optional<Cliente> exisCli = repoCliente.findById(cuentaDto.getCliente().getId());
				if (exisCli.isPresent()) {
					if (cuentaDto.getTipoCuenta() != 1 && cuentaDto.getTipoCuenta() != 2) {
						result.rejectValue("tipoCuenta", "Matches",
								"El tipo de cuenta debe ser: 1-Ahorros o 2-Corriente.");
						throw new InvalidDataException(result);
					} else {
						update.setNumeroCuenta(cuentaDto.getNumeroCuenta());
						update.setTipoCuenta(cuentaDto.getTipoCuenta());
						update.setSaldoInicial(cuentaDto.getSaldoInicial());
						update.setEstado(cuentaDto.getEstado());
						repoCuenta.save(update);
						return returnCuentaDto(update);
					}
				} else {
					result.rejectValue("id", "Noexiste", "No existe el cliente con el id ingresado.");
					throw new InvalidDataException(result);
				}
			}

		} else {
			result.rejectValue("id", "Noexiste", "No existe la cuenta con el id ingresado.");
			throw new InvalidDataException(result);
		}

	}

	// Función que retorna una cuenta
	public CuentasDto getCuenta(int idCuenta) {
		Optional<Cuenta> existe = repoCuenta.findById(idCuenta);

		if (existe.isPresent()) {
			return returnCuentaDto(existe.get());
		} else {
			throw new IllegalArgumentException("No existe la cuenta con el id ingresado.");
		}
	}

	// Función que elimina una cuenta
	public void deleteCuenta(int idCuenta) {
		Optional<Cuenta> existe = repoCuenta.findById(idCuenta);

		if (existe.isPresent()) {
			repoCuenta.delete(existe.get());
		} else {
			throw new IllegalArgumentException("No existe la cuenta con el id ingresado.");
		}
	}

	// Función que retorna la lista de cuentas
	public List<CuentasDto> getCuentas() {
		List<Cuenta> list = repoCuenta.findAll();
		if (list.isEmpty()) {
			throw new IllegalArgumentException("No existen cuentas en BD.");
		}
		return returnListaCuentasDto(list);
	}

	public List<CuentasDto> returnListaCuentasDto(List<Cuenta> lista) {

		List<CuentasDto> cuentas = new ArrayList<CuentasDto>();
		lista.stream().forEach(cuenta -> {
			CuentasDto dto = new CuentasDto();
			dto.setId(cuenta.getId());
			dto.setNumeroCuenta(cuenta.getNumeroCuenta());
			dto.setTipoCuenta(cuenta.getTipoCuenta());
			dto.setEstado(cuenta.getEstado());
			dto.setSaldoInicial(cuenta.getSaldoInicial());
			dto.setCliente(returnClienteDto(cuenta.getCliente()));
			dto.setMovimientos(returnListMovimientosDto(cuenta.getMovimientos()));
			cuentas.add(dto);
		});

		return cuentas;
	}

	// Función que registra un movimiento
	public MovimientosDto crearMovimiento(MovimientosDto movimiento, BindingResult result) {

		// Buscamos si existe la cuenta
		Optional<Cuenta> exisCue = repoCuenta.findById(movimiento.getCuenta().getId());
		if (exisCue.isPresent()) {
			Cuenta cuenta = exisCue.get();
			if (movimiento.getTipoMovimiento() != 1 && movimiento.getTipoMovimiento() != 2) {
				result.rejectValue("tipoMovimiento", "Matches",
						"El tipo de movimiento debe ser: 1-Credito o 2-Debito.");
				throw new InvalidDataException(result);
			} else {
				switch (movimiento.getTipoMovimiento()) {
				case 1:
					// suma
					cuenta.setSaldoInicial(cuenta.getSaldoInicial().add(movimiento.getValor()));
					Movimientos save = new Movimientos(movimiento.getFecha(), movimiento.getTipoMovimiento(),
							movimiento.getValor(), cuenta);
					save = repoMovimientos.save(save);
					return returnMovimientosDto(save);
				default:
					// resta
					if (cuenta.getSaldoInicial().compareTo(BigDecimal.ZERO) == 0) {
						result.rejectValue("cuenta.saldoInicial", "Matches", "Saldo no disponible.");
						throw new InvalidDataException(result);
					} else {
						if (movimiento.getValor().compareTo(cuenta.getSaldoInicial()) == 1) {
							// Es mayor lo que va a debitar del saldo
							result.rejectValue("cuenta.saldoInicial", "Matches", "Saldo no disponible.");
							throw new InvalidDataException(result);
						} else {
							cuenta.setSaldoInicial(cuenta.getSaldoInicial().subtract(movimiento.getValor()));
							Movimientos saveDeb = new Movimientos(movimiento.getFecha(), movimiento.getTipoMovimiento(),
									movimiento.getValor(), cuenta);
							saveDeb = repoMovimientos.save(saveDeb);
							return returnMovimientosDto(saveDeb);
						}
					}
				}
			}

		} else {
			result.rejectValue("id", "Noexiste", "No existe la cuenta con el id ingresado.");
			throw new InvalidDataException(result);
		}

	}

	// Función que retorna el listado de cuenta de un cliente en un rango de fechas
	public List<CuentasDto> listCuentasByCliente(int idCliente, Date fechaInicial, Date fechaFinal) {

		// Buscamos si existe la persona
		Optional<Cliente> exisCli = repoCliente.findById(idCliente);
		if (exisCli.isPresent()) {
			Cliente cliente = exisCli.get();
			List<Cuenta> cuentas = repoCuenta.findByClienteAndFechas(cliente);
			if (cuentas != null && !cuentas.isEmpty()) {
				return returnReporte(cuentas, fechaInicial, fechaFinal);
			} else {
				throw new IllegalArgumentException("No existen cuentas asociadas a este cliente.");
			}

		} else {
			throw new IllegalArgumentException("No existe el cliente con el id ingresado.");
		}
	}

	public List<CuentasDto> returnReporte(List<Cuenta> lista, Date fechaInicial, Date fechaFinal) {

		List<CuentasDto> cuentas = new ArrayList<CuentasDto>();
		lista.stream().forEach(cuenta -> {
			CuentasDto dto = new CuentasDto();
			dto.setId(cuenta.getId());
			dto.setNumeroCuenta(cuenta.getNumeroCuenta());
			dto.setTipoCuenta(cuenta.getTipoCuenta());
			dto.setEstado(cuenta.getEstado());
			dto.setSaldoInicial(cuenta.getSaldoInicial());
			dto.setCliente(returnClienteDto(cuenta.getCliente()));

			List<Movimientos> movimientos = cuenta.getMovimientos().stream()
					.filter(m -> m.getFecha().after(fechaInicial) && m.getFecha().before(fechaFinal))
					.collect(Collectors.toList());

			dto.setMovimientos(returnListMovimientosDto(movimientos));
			cuentas.add(dto);
		});

		return cuentas;
	}

	// Función que elimina un movimiento
	public void deleteMovimiento(int idMovimiento) {
		Optional<Movimientos> existe = repoMovimientos.findById(idMovimiento);

		if (existe.isPresent()) {
			repoMovimientos.delete(existe.get());
		} else {
			throw new IllegalArgumentException("No existe el movimiento con el id ingresado.");
		}
	}

	// Función que retorna un movimiento
	public MovimientosDto getMovimiento(int idMovimiento) {
		Optional<Movimientos> existe = repoMovimientos.findById(idMovimiento);

		if (existe.isPresent()) {
			return returnMovimientosDto(existe.get());
		} else {
			throw new IllegalArgumentException("No existe el movimiento con el id ingresado.");
		}
	}

	// Función que retorna la lista de movimientos
	public List<MovimientosDto> getMovimientos() {
		List<Movimientos> list = repoMovimientos.findAll();
		if (list.isEmpty()) {
			throw new IllegalArgumentException("No existen movimientos en BD.");
		}
		return returnListMovimientosDto(list);
	}

}