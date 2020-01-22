package com.operationbanking.app.expose;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.operationbanking.app.business.ICustomerProductService;
import com.operationbanking.app.models.CustomerBankingProduct;
import com.operationbanking.app.models.ReporteProductoSaldoDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers-products")
public class CustomerProductController {
	@Autowired
	private ICustomerProductService clienteProductosService;
	
	@GetMapping("/{idCliente}")
	public Flux<CustomerBankingProduct> listarProductoxCliente(@PathVariable String idCliente) {
		return clienteProductosService.findByCliente(idCliente);
	}
	
	@GetMapping("/reporte/{idCliente}")
	public Flux<ReporteProductoSaldoDTO> reporteProductosSaldo(@PathVariable String idCliente) {
		return clienteProductosService.reporteProductosSaldo(idCliente);
	}
	
	@PostMapping
	public Mono<CustomerBankingProduct> registrarClienteProductoBancario(@RequestBody @Valid CustomerBankingProduct clienteProductoBancario) {
		return clienteProductosService.saveClienteProductoBancario(clienteProductoBancario);
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
