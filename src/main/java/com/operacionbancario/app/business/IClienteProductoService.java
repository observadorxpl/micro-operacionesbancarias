package com.operacionbancario.app.business;

import com.operacionbancario.app.models.ClienteProductoBancario;
import com.operacionbancario.app.models.ReporteProductoSaldoDTO;
import com.operacionbancario.app.util.ICRUD;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClienteProductoService extends ICRUD<ClienteProductoBancario>{
	public Mono<ClienteProductoBancario> saveClienteProductoBancario(ClienteProductoBancario clienteProducto);
	public Flux<ClienteProductoBancario> findByCliente(String idCliente);
	public Flux<ReporteProductoSaldoDTO> reporteProductosSaldo(String idCliente);

}
