package com.operationbanking.app.expose;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operationbanking.app.business.IOperationService;
import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.OperacionBancariaDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/operations")
public class OperationController {
	@Autowired
	private IOperationService operacionesService;
	
	@PostMapping(value = "/deposito")
	public Mono<BankingMovement> deposito(@RequestBody @Valid OperacionBancariaDTO dto){
		return operacionesService.depositoV2(dto);
	}
	
	@PostMapping(value = "/retiro")
	public Mono<BankingMovement> retiro(@RequestBody @Valid OperacionBancariaDTO dto){
		return operacionesService.retiroV3(dto);
	}
}
