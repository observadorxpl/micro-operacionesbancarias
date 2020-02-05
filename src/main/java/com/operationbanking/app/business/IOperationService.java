package com.operationbanking.app.business;

import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.OperacionBancariaDTO;
import com.operationbanking.app.models.TypeOperation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOperationService{
	public Mono<BankingMovement> retiro (OperacionBancariaDTO dto);
	//public Mono<BankingMovement> retiroV3 (OperacionBancariaDTO dto);
	public Mono<BankingMovement> deposito (OperacionBancariaDTO dto);
	public Mono<BankingMovement> transferenciaMismoBanco (OperacionBancariaDTO dto);
	public Mono<BankingMovement> transferenciaOtrosBancos (OperacionBancariaDTO dto);
	public Flux<TypeOperation> listarTipoOperaciones();
	//public Mono<BankingMovement> depositoV2 (OperacionBancariaDTO dto);
}
