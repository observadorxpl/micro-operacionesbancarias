package com.operacionbancario.app.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.operacionbancario.app.models.MovimientoBancario;

import reactor.core.publisher.Flux;

public interface IMovimientoRepo extends ReactiveMongoRepository<MovimientoBancario, String>{
	@Query("{'clienteProductoBancario.cliente.idCliente' : ?0}")
	Flux<MovimientoBancario> buscarPorIdCliente(String idCliente);
}
