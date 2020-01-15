package com.operacionbancario.app.expose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.shared.Application;
import com.operacionbancario.app.business.IMovimientoService;
import com.operacionbancario.app.models.MovimientoBancario;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movimientos")
public class MovimientoRestController {
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	@Autowired
	private IMovimientoService service;
	
	@GetMapping("/{id}")
	public Flux<MovimientoBancario> listarMovimientos(@PathVariable String id){
		return service.listarMovimientosCliente(id);
	}
}
