package com.operationbanking.app.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.operationbanking.app.dto.Customer;
import com.operationbanking.app.models.CustomerBankingProduct;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public interface ICustomerBankingProductRepository extends ReactiveMongoRepository<CustomerBankingProduct, String>{
	public Flux<CustomerBankingProduct> findByCustomer(Customer cutomer);
	public Flux<CustomerBankingProduct> findByCustomer(String idCustomer);
	public Mono<CustomerBankingProduct> findByAccountNumber(String accountNumber);
	public Mono<CustomerBankingProduct> findByAccountNumberCCI(String accountNumberCCI);
	//@Query("{'customer.idCustomer' : ?0 , 'customer.customerType.customerTypeCode' : ?1 , 'bankingProduct.productCode' : ?2, 'bank.codeBank': ?3 }")
	//public Flux<CustomerBankingProduct> buscarPorCodigoTipoClienteIdTipoProducto(String idCustomer, Integer customerTypeCode, Integer productCode, Integer bankCode);
	
	//@Query(" {'customer.dni' : ?0 , 'bank.codeBank': ?1 } ")
	//public Flux<CustomerBankingProduct> buscarPorDniYCodigoBanco(String dni, Integer codeBank);
	
	//@Query(" {'customer.dni' : ?0 } ")
	//public Flux<CustomerBankingProduct> buscarPorDni(String dni);
	
	//@Query(" {'bank.codeBank': ?0} ")
	//public Flux<CustomerBankingProduct> buscarPorCodigoBanco(Integer codeBank);
	public Flux<CustomerBankingProduct> findByCustomer_IdCustomerAndCustomer_CustomerType_CustomerTypeCodeAndBankingProduct_ProductCodeAndBank_CodeBank(String idCustomer, Integer customerTypeCode, Integer productCode, Integer bankCode);
	public Flux<CustomerBankingProduct> findByCustomer_DniAndBank_CodeBank(String dni, Integer codeBank);
	public Flux<CustomerBankingProduct> findByCustomer_Dni(String dni);
	public Flux<CustomerBankingProduct> findByBank_CodeBank(Integer codeBank);

	
	
}
