package com.neoris.banco.controller;

import static org.mockito.Mockito.mock;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import com.neoris.banco.dto.ClienteDto;
import com.neoris.banco.dto.CuentasDto;
import com.neoris.banco.model.Cliente;
import com.neoris.banco.repository.ClienteRepository;
import com.neoris.banco.repository.CuentaRepository;
import com.neoris.banco.services.BancoService;


@ExtendWith(MockitoExtension.class)
public class CuentaControllerTest {

	  @InjectMocks
	  private BancoService services;
	  
	  @Mock
	  private CuentaRepository repoCuenta;
	  
	  @Mock
	  private ClienteRepository repoCliente;
	  
	  
	  @BeforeEach
	  public void setUp() {
		  
	  }
	  
	    @Test
	    public void crearCuentaOk() {
	    	ClienteDto dto = new ClienteDto();
	    	dto.setId(1);
	    	dto.setIdentificacion("1");
	    	
	    	CuentasDto nueva = new CuentasDto();
	    	nueva.setNumeroCuenta("1");
	    	nueva.setCliente(dto);
	    	nueva.setTipoCuenta(1);
	
	        BindingResult result = mock(BindingResult.class);
	        
	        Cliente cliente = new Cliente();
	        cliente.setId(1);
	        cliente.setIdentificacion("1");
	        
	        Mockito.when(repoCuenta.findByNumeroCuenta(nueva.getNumeroCuenta())).thenReturn(Optional.empty());
	        Mockito.when(repoCliente.findById(nueva.getCliente().getId())).thenReturn(Optional.of(cliente));
	        CuentasDto creada = services.crearCuenta(nueva, result);
	        Assertions.assertEquals(creada.getNumeroCuenta(), nueva.getNumeroCuenta());
	    }
} 
