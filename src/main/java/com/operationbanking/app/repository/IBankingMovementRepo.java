package com.operationbanking.app.repository;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.operationbanking.app.models.BankingMovement;

import reactor.core.publisher.Flux;

public interface IBankingMovementRepo extends ReactiveMongoRepository<BankingMovement, String> {
	@Query("{'customerBankingProduct.customer.idCustomer' : ?0}")
	Flux<BankingMovement> buscarPorIdCliente(String idCliente);

	@Query("{'customerBankingProduct.accountNumber' : ?0, 'movementDate' : ?1}")
	Flux<BankingMovement> buscarPorNumeroCuenta(String numeroCuenta, String tipoOperacion);

	@Query("{'customerBankingProduct.accountNumber' : ?0, 'movementDate' : { $gte: ?1, $lte: ?2 }, 'interests': { $gt: ?3 } }")
	Flux<BankingMovement> buscarPorNumeroCuentaYRangoFechas(String numeroCuenta, Date rangoInicio,
			Date rangoFin, Double intereses);
	
	@Query("{'movementDate' : { $gte: ?0, $lte: ?1 } }")                 
	public Flux<BankingMovement> buscarPorRangoFechas(Date rangoInicio, Date rangoFin); 
}
