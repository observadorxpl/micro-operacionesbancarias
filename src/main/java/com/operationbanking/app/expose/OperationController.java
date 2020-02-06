package com.operationbanking.app.expose;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operationbanking.app.business.IOperationService;
import com.operationbanking.app.dto.OperacionBancariaDTO;
import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.TypeOperation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Api(value = "Banking Operation Microservice")
@RequestMapping(value = "/operations")
public class OperationController {
	private Logger log = LoggerFactory.getLogger(OperationController.class);


	@Autowired
	private IOperationService operacionesService;
	
	
	@PostMapping(value = "/deposito")
	@ApiOperation(value = "Make a deposit", notes="Make a deposit, need the structure dto: numeroCuentaOrigen, numeroCuentaDestino and monto")
	public Mono<BankingMovement> deposito(@RequestBody @Valid OperacionBancariaDTO dto){
		return operacionesService.deposito(dto);
	}
	
	@PostMapping(value = "/retiro")
	@ApiOperation(value = "Make a withdrawal", notes="Make a withdrawal, need the structure dto: numeroCuentaOrigen, numeroCuentaDestino and monto")
	public Mono<BankingMovement> retiro(@RequestBody @Valid OperacionBancariaDTO dto){
		return operacionesService.retiro(dto);
	}
	
	@PostMapping(value = "/mismo-banco")
	@ApiOperation(value = "Make a operation same bank", notes="Make a operation, need the structure dto: numeroCuentaOrigen, numeroCuentaDestino and monto")
	public Mono<BankingMovement> transferenciaMismoBanco(@RequestBody @Valid OperacionBancariaDTO dto){
		return operacionesService.transferenciaMismoBanco(dto);
	}
	
	@PostMapping(value = "/otros-bancos")
	@ApiOperation(value = "Make a operation other bank", notes="Make a operation, need the structure dto: numeroCuentaOrigen, numeroCuentaCCIDestino and monto")
	public Mono<BankingMovement> transferenciaOtrosBancos(@RequestBody @Valid OperacionBancariaDTO dto){
		return operacionesService.transferenciaOtrosBancos(dto);
	}
	
	@PostMapping(value = "/pago-tarjeta")
	@ApiOperation(value = "Make a pay card", notes="Make a operation, need the structure dto: numeroCuentaOrigen, numeroTarjetaDestino and monto, typeOperation reference")
	public Mono<BankingMovement> pagarTarjetaCredito(@RequestBody @Valid OperacionBancariaDTO dto){
		log.info("OperationController pago-tarjeta, OperacionBancariadto: " + dto);
		return operacionesService.pagoTarjetaCredito(dto);
	}
	
		
	@GetMapping(value = "/tipo-operaciones")
	@ApiOperation(value = "List All type operations", notes="List All type operation")
	public Flux<TypeOperation> listarTipoDeOperaciones(){
		return operacionesService.listarTipoOperaciones();
	}
	
}
