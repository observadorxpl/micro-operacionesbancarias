package com.operationbanking.app.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operationbanking.app.business.IBankingMovementService;
import com.operationbanking.app.dto.ConsultaComisionesDTO;
import com.operationbanking.app.dto.ReporteComisionesDTO;
import com.operationbanking.app.models.BankingMovement;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;

@RestController
@Api(value = "Banking Operation Microservice")
@RequestMapping("/movements")
public class MovementController {
	@Autowired
	private IBankingMovementService service;
	
	@GetMapping("/{idCliente}")
	@ApiOperation(value = "List movements by client", notes="List movements by client's id")
	public Flux<BankingMovement> listarMovimientos(@PathVariable String idCliente){
		return service.listarMovimientosCliente(idCliente);
	}
	
	@PostMapping(value = "/reporte-comisiones")
	@ApiOperation(value = "List account's movements with commission charge", notes="Need structure dto: numeroCuenta, numeroCuenta, rangoFin")
	public Flux<ReporteComisionesDTO> reporteMovimientosPorRangoFecha(@RequestBody ConsultaComisionesDTO dto){
		return service.listarMovimientosPorRangoFecha(dto);
	}
}
