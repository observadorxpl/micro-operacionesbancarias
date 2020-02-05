package com.operationbanking.app.repository;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.TypeOperation;

import reactor.core.publisher.Flux;

public interface IBankingMovementRepo extends ReactiveMongoRepository<BankingMovement, String> {
	@Query("{'customerBankingProduct.customer.idCustomer' : ?0}")
	Flux<BankingMovement> buscarPorIdCliente(String idCustomer);

	@Query("{'customerBankingProduct.accountNumber' : ?0, 'typeOperation.codeTypeOperation' : ?1}")
	Flux<BankingMovement> buscarPorNumeroCuenta(String accountNumber, Integer codeTypeOperation);

	@Query("{'customerBankingProduct.accountNumber' : ?0, 'movementDate' : { $gte: ?1, $lte: ?2 }, 'interests': { $gt: ?3 } }")
	Flux<BankingMovement> buscarPorNumeroCuentaYRangoFechas(String accountNumber, Date rangoInicio,
			Date rangoFin, Double interests);
	
	@Query("{'movementDate' : { $gte: ?0, $lte: ?1 } }")                 
	public Flux<BankingMovement> buscarPorRangoFechas(Date rangoInicio, Date rangoFin); 
}
