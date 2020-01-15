package com.operacionbancario.app.business;

import com.operacionbancario.app.models.CuentaBancaria;
import com.operacionbancario.app.models.OperacionBancariaDTO;

import reactor.core.publisher.Mono;

public interface IOperacionesService{
	public Mono<CuentaBancaria> retiro (OperacionBancariaDTO dto);
	public Mono<CuentaBancaria> deposito (OperacionBancariaDTO dto);
}
