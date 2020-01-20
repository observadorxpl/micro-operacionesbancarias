package com.operacionbancario.app.business;

import com.operacionbancario.app.models.ConsultaComisionesDTO;
import com.operacionbancario.app.models.MovimientoBancario;
import com.operacionbancario.app.models.ReporteComisionesDTO;
import com.operacionbancario.app.util.ICRUD;

import reactor.core.publisher.Flux;

public interface IMovimientoService extends ICRUD<MovimientoBancario>{
	Flux<MovimientoBancario> listarMovimientosCliente(String idCliente);
	
	Flux<ReporteComisionesDTO> listarMovimientosPorRangoFecha(ConsultaComisionesDTO dto);
	
	Flux<MovimientoBancario> buscarPorRangoFechas (ConsultaComisionesDTO dto);
}
