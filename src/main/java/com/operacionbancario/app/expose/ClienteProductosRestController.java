package com.operacionbancario.app.expose;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operacionbancario.app.business.IClienteProductoService;
import com.operacionbancario.app.models.ClienteProductosBancarioDTO;
import com.operacionbancario.app.models.CuentaBancaria;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientesProductos")
public class ClienteProductosRestController {
	@Autowired
	private IClienteProductoService clienteProductosService;
	
	@GetMapping
	public Mono<ClienteProductosBancarioDTO> listarProductoxCliente(ClienteProductosBancarioDTO dto) {
		return clienteProductosService.findByCliente(dto.getCliente());
	}
	
	@PostMapping
	public Flux<CuentaBancaria> registrarClienteProductosBancarios(@RequestBody ClienteProductosBancarioDTO clienteProductoDTO) {
		return clienteProductosService.saveClienteProductoDTOv2(clienteProductoDTO);
	}
	/*
	@Autowired
	private IClienteProductosService clienteProductosService;

	@GetMapping
	public Flux<ClienteProductos> listarAllClientes() {
		return clienteProductosService.findAll();
	}

	@GetMapping("/{id}")
	public Mono<ClienteProductos> buscarCliente(@PathVariable String id) {
		return clienteProductosService.finById(id);
	}

	@PostMapping
	public Mono<ClienteProductos> registrarCliente(@RequestBody ClienteProductos clienteProductos) {
		return clienteProductosService.save(clienteProductos);
	}

	@PutMapping
	public Mono<ClienteProductos> actualizarCliente(@RequestBody ClienteProductos clienteProductos) {
		return clienteProductosService.save(clienteProductos);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> eliminarCliente(@PathVariable String id){
		return clienteProductosService.deleteById(id);
	}
	*/
}
