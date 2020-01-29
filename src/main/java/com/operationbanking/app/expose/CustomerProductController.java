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
import com.operationbanking.app.consolidado.ReporteConsolidadoDTO;
import com.operationbanking.app.models.CustomerBankingProduct;
import com.operationbanking.app.models.ReporteProductoSaldoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Api(value = "Banking Operation Microservice")
@RequestMapping("/customers-products")
public class CustomerProductController {
	@Autowired
	private ICustomerProductService clienteProductosService;

	@GetMapping("/{idCliente}")
	@ApiOperation(value = "List products by client", notes="List all products by client's id")
	public Flux<CustomerBankingProduct> listarProductoxCliente(@PathVariable String idCliente) {
		return clienteProductosService.findByCliente(idCliente);
	}

	@GetMapping("/reporte/{idCliente}")
	@ApiOperation(value = "Generate a balance's report by client", notes="Generate a balance's report by client's id ")
	public Flux<ReporteProductoSaldoDTO> reporteProductosSaldo(@PathVariable String idCliente) {
		return clienteProductosService.reporteProductosSaldo(idCliente);
	}
	
	@GetMapping("/consolidado/{idCliente}")
	@ApiOperation(value = "Generate a consolidated report by client", notes="Generate a consolidated report by client's id ")
	public Mono<ReporteConsolidadoDTO> consolidado(@PathVariable String idCliente) {
		return clienteProductosService.reporteConsolidadov2(idCliente);
	}
	
	@GetMapping("/cliente-productos/{dni}")
	@ApiOperation(value = "List products by client's dni")
	public Flux<CustomerBankingProduct> listarProductosDelCliente(@PathVariable String dni) {
		return clienteProductosService.productosXCodigoBanco(dni);
	}

	@PostMapping
	@ApiOperation(value = "Save a customer with a product", notes = "Save and return a customer with a product, need customer, product and bank references, min(ids)")
	public Mono<CustomerBankingProduct> registrarClienteProductoBancario(
			@RequestBody @Valid CustomerBankingProduct clienteProductoBancario) {
		return clienteProductosService.saveClienteProductoBancario(clienteProductoBancario);
	}
}
