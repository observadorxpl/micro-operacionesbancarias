package com.operacionbancario.app.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.ClienteProductoBancario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public interface IClienteProductoRepository extends ReactiveMongoRepository<ClienteProductoBancario, String>{
	public Flux<ClienteProductoBancario> findByCliente(Cliente cliente);
	public Flux<ClienteProductoBancario> findByCliente(String idCliente);
	public Mono<ClienteProductoBancario> findByNumeroCuenta(String numeroCuenta);
	
	@Query("{'cliente.idCliente' : ?0 , 'cliente.tipoCliente.codigoTipoCliente' : ?1 , 'productoBancario.codigoProducto' : ?2}")
	public Flux<ClienteProductoBancario> buscarPorCodigoTipoClienteIdTipoProducto(String idCliente, Integer codigoTipoCliente, Integer idTipoProducto);

}
