package com.operacionbancario.app.business;

import com.operacionbancario.app.models.MovimientoBancario;
import com.operacionbancario.app.models.OperacionBancariaDTO;

import reactor.core.publisher.Mono;

public interface IOperacionesService{
	public Mono<MovimientoBancario> retiro (OperacionBancariaDTO dto);
	public Mono<MovimientoBancario> retiroV3 (OperacionBancariaDTO dto);
	public Mono<MovimientoBancario> deposito (OperacionBancariaDTO dto);
	public Mono<MovimientoBancario> depositoV2 (OperacionBancariaDTO dto);
}
