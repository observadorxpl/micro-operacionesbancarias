package com.operationbanking.app.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operationbanking.app.business.IBankingMovementService;
import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.ConsultaComisionesDTO;
import com.operationbanking.app.models.ReporteComisionesDTO;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movements")
public class MovementController {
	@Autowired
	private IBankingMovementService service;
	
	@GetMapping("/{idCliente}")
	public Flux<BankingMovement> listarMovimientos(@PathVariable String idCliente){
		return service.listarMovimientosCliente(idCliente);
	}
	@PostMapping(value = "/reporte-comisiones")
	public Flux<ReporteComisionesDTO> reporteMovimientosPorRangoFecha(@RequestBody ConsultaComisionesDTO dto){
		System.out.println("[REPORTE COMISION]: " + dto);
		return service.listarMovimientosPorRangoFecha(dto);
		//return service.buscarPorRangoFechas(dto);
	}
}
