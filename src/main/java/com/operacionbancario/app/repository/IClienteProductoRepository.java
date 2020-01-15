package com.operacionbancario.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.ClienteProductoBancario;

import reactor.core.publisher.Flux;
@Repository
public interface IClienteProductoRepository extends ReactiveMongoRepository<ClienteProductoBancario, String>{
	public Flux<ClienteProductoBancario> findByCliente(Cliente cliente);
}
