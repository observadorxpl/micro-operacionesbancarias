package com.operacionbancario.app.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.discovery.shared.Application;
import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.MovimientoBancario;
import com.operacionbancario.app.repository.IMovimientoRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovimientoServiceImpl implements IMovimientoService{
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	@Autowired
	private IMovimientoRepo movimientoRepo;
	

	@Override
	public Flux<MovimientoBancario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<MovimientoBancario> finById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<MovimientoBancario> save(MovimientoBancario t) {
		return movimientoRepo.save(t);
	}

	@Override
	public Mono<Void> delete(MovimientoBancario t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> deleteById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<MovimientoBancario> listarMovimientosCliente(String idCliente) {
		return WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/").build()
		.get().uri(idCliente).retrieve().bodyToMono(Cliente.class).log().flux()
		.defaultIfEmpty(new Cliente())
		.flatMap(c -> {
			if(c.getIdCliente() == null) {
				return Mono.error(new InterruptedException("El cliente no existe"));
			}
			return Flux.just(c);
		})
		.flatMap(c -> {
			return movimientoRepo.findByCliente(c);
		});
	}

}
