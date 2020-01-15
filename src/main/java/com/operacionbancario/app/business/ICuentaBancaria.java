package com.operacionbancario.app.business;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.CuentaBancaria;
import com.operacionbancario.app.util.ICRUD;

import reactor.core.publisher.Flux;

public interface ICuentaBancaria extends ICRUD<CuentaBancaria>{
	public Flux<CuentaBancaria> listarProductosXCliente(Cliente cliente);

}
