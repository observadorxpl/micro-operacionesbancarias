package com.operacionbancario.app.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operacionbancario.app.business.IMovimientoService;
import com.operacionbancario.app.models.MovimientoBancario;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movimientos")
public class MovimientoRestController {
	@Autowired
	private IMovimientoService service;
	
	@GetMapping("/{idCliente}")
	public Flux<MovimientoBancario> listarMovimientos(@PathVariable String idCliente){
		return service.listarMovimientosCliente(idCliente);
	}
}
