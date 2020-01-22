package com.operationbanking.app.business;

import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.ConsultaComisionesDTO;
import com.operationbanking.app.models.ReporteComisionesDTO;
import com.operationbanking.app.util.ICRUD;

import reactor.core.publisher.Flux;

public interface IBankingMovementService extends ICRUD<BankingMovement>{
	Flux<BankingMovement> listarMovimientosCliente(String idCliente);
	
	Flux<ReporteComisionesDTO> listarMovimientosPorRangoFecha(ConsultaComisionesDTO dto);
	
	Flux<BankingMovement> buscarPorRangoFechas (ConsultaComisionesDTO dto);
}
