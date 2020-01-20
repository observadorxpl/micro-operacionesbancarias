package com.operacionbancario.app.repository;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.operacionbancario.app.models.MovimientoBancario;

import reactor.core.publisher.Flux;

public interface IMovimientoRepo extends ReactiveMongoRepository<MovimientoBancario, String> {
	@Query("{'clienteProductoBancario.cliente.idCliente' : ?0}")
	Flux<MovimientoBancario> buscarPorIdCliente(String idCliente);

	@Query("{'clienteProductoBancario.numeroCuenta' : ?0, 'tipoOperacion' : ?1}")
	Flux<MovimientoBancario> buscarPorNumeroCuenta(String numeroCuenta, String tipoOperacion);

	@Query("{'clienteProductoBancario.numeroCuenta' : ?0, 'fechaMovimiento' : { $gte: ?1, $lte: ?2 }, 'interes': { $gt: ?3 } }")
	Flux<MovimientoBancario> buscarPorNumeroCuentaYRangoFechas(String numeroCuenta, Date rangoInicio,
			Date rangoFin, Double intereses);
	
	@Query("{'fechaMovimiento' : { $gte: ?0, $lte: ?1 } }")                 
	public Flux<MovimientoBancario> buscarPorRangoFechas(Date rangoInicio, Date rangoFin); 
}
