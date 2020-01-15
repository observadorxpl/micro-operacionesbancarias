package com.operacionbancario.app.business;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.ClienteProductoBancario;
import com.operacionbancario.app.models.ClienteProductosBancarioDTO;
import com.operacionbancario.app.models.CuentaBancaria;
import com.operacionbancario.app.util.ICRUD;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClienteProductoService extends ICRUD<ClienteProductoBancario>{
	public Mono<ClienteProductosBancarioDTO> saveClienteProductoDTO(ClienteProductosBancarioDTO dto);
	public Flux<CuentaBancaria> saveClienteProductoDTOv2(ClienteProductosBancarioDTO dto);
	public Flux<CuentaBancaria> saveClienteProductoDTOv3(ClienteProductosBancarioDTO dto);
	public Flux<CuentaBancaria> saveClienteProductoDTOv4(ClienteProductosBancarioDTO dto);

	public Mono<ClienteProductosBancarioDTO> findByCliente(Cliente cliente);
	public Mono<ClienteProductosBancarioDTO> findByClientev2(Cliente cliente);


}
