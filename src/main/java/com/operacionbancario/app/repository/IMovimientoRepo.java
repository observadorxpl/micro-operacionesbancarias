package com.operacionbancario.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.MovimientoBancario;

import reactor.core.publisher.Flux;

public interface IMovimientoRepo extends ReactiveMongoRepository<MovimientoBancario, String>{
	Flux<MovimientoBancario> findByCliente(Cliente cliente);
}
