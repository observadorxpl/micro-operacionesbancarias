package com.operationbanking.app.business;

import com.operationbanking.app.models.CustomerBankingProduct;
import com.operationbanking.app.models.ReporteProductoSaldoDTO;
import com.operationbanking.app.util.ICRUD;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerProductService extends ICRUD<CustomerBankingProduct>{
	public Mono<CustomerBankingProduct> saveClienteProductoBancario(CustomerBankingProduct clienteProducto);
	public Flux<CustomerBankingProduct> findByCliente(String idCliente);
	public Flux<ReporteProductoSaldoDTO> reporteProductosSaldo(String idCliente);

}