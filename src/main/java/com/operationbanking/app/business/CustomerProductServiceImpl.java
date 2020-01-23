package com.operationbanking.app.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.operationbanking.app.models.BankingProduct;
import com.operationbanking.app.models.Customer;
import com.operationbanking.app.models.CustomerBankingProduct;
import com.operationbanking.app.models.ReporteProductoSaldoDTO;
import com.operationbanking.app.repository.ICustomerBankingProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerProductServiceImpl implements ICustomerProductService {

	@Autowired
	private ICustomerBankingProductRepository customerProductRepo;
	
	@Value("${com.bootcamp.gateway.url}")
	private String gatewayUrlPort;
	
	@Override
	public Flux<CustomerBankingProduct> findAll() {
		return customerProductRepo.findAll();
	}

	@Override
	public Mono<CustomerBankingProduct> finById(String id) {
		return customerProductRepo.findById(id);
	}

	@Override
	public Mono<CustomerBankingProduct> save(CustomerBankingProduct t) {
		return null;
	}

	@Override
	public Mono<Void> delete(CustomerBankingProduct t) {
		return customerProductRepo.delete(t);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return customerProductRepo.deleteById(id);
	}

	@Override
	public Flux<ReporteProductoSaldoDTO> reporteProductosSaldo(String idCliente) {
		return WebClient.builder().baseUrl("http://"+gatewayUrlPort+"/micro-clientes/customers/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Customer.class).log().flatMapMany(cli -> {
					return customerProductRepo.findByCustomer(cli);
				}).flatMap(clPro -> {
					return Flux.just(new ReporteProductoSaldoDTO(clPro.getBankingProduct().getDescription(),
							clPro.getBalance()));
				});
	}

	@Override
	public Flux<CustomerBankingProduct> findByCliente(String idCliente) {
		return WebClient.builder().baseUrl("http://"+gatewayUrlPort+"/micro-clientes/customers/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Customer.class).log().flatMapMany(cli -> {
					return customerProductRepo.findByCustomer(cli);
				});
	}

	@Override
	public Mono<CustomerBankingProduct> saveClienteProductoBancario(CustomerBankingProduct clienteProducto) {
		return WebClient.builder().baseUrl("http://"+gatewayUrlPort+"/micro-clientes/customers/").build().get()
				.uri(clienteProducto.getCustomer().getIdCustomer()).retrieve().bodyToMono(Customer.class).log()
				.flatMap(cl -> {
					clienteProducto.setCustomer(cl);
					return WebClient.builder().baseUrl("http://"+gatewayUrlPort+"/micro-bancario/products/").build().get()
							.uri(clienteProducto.getBankingProduct().getIdProduct()).retrieve()
							.bodyToMono(BankingProduct.class).log();
				}).flatMap(p -> {
					clienteProducto.setBankingProduct(p);
					return customerProductRepo
							.buscarPorCodigoTipoClienteIdTipoProducto(clienteProducto.getCustomer().getIdCustomer(),
									clienteProducto.getCustomer().getCustomerType().getCustomerTypeCode(),
									clienteProducto.getBankingProduct().getProductCode()).doOnNext(clPro -> System.out.println("[customerProductRepo]: "+clPro))
							.count();
				}).flatMap(count -> {
					System.out.println("[count]" + count);
					// SI EL CLIENTE ES PERSONAL
					if (clienteProducto.getCustomer().getCustomerType().getCustomerTypeCode() == 2) {
						if (clienteProducto.getBankingProduct().getProductCode() == 1
								|| clienteProducto.getBankingProduct().getProductCode() == 2
								|| clienteProducto.getBankingProduct().getProductCode() == 3) {
							if (count > 0) {
								String msgError = "El cliente Personal Ya tiene asignado: "
										+ clienteProducto.getBankingProduct().getDescription();
								return Mono.error(new InterruptedException(msgError));
							}
							return customerProductRepo.save(generateAccountNumberAndBalance(clienteProducto));
						}
						String msgError = "El cliente Personal No puede puede tener asignado: "
								+ clienteProducto.getBankingProduct().getDescription();
						return Mono.error(new InterruptedException(msgError));
						// SI EL CLIENTE ES EMPRESARIAL
					} else if (clienteProducto.getCustomer().getCustomerType().getCustomerTypeCode() == 1) {
						// SI EL PRODUCTO B. ES: CUENTA AHORRO(1) O CUENTA A PLAZO FIJO(3)
						if (clienteProducto.getBankingProduct().getProductCode() != 2) {
							String msgError = "El cliente Empresarial No puede puede tener asignado: "
									+ clienteProducto.getBankingProduct().getDescription();
							return Mono.error(new InterruptedException(msgError));
						}
						return customerProductRepo.save(generateAccountNumberAndBalance(clienteProducto));
						// SI EL CLIENTE ES PERSONA VIP
					} else if (clienteProducto.getCustomer().getCustomerType().getCustomerTypeCode() == 3) {
						// SI EL PRODUCTO B. ES: EMP. PYME(6) O EMP. CORPORATIVE(7)
						if (clienteProducto.getBankingProduct().getProductCode() == 1
								|| clienteProducto.getBankingProduct().getProductCode() == 2
								|| clienteProducto.getBankingProduct().getProductCode() == 3
								|| clienteProducto.getBankingProduct().getProductCode() == 6
								|| clienteProducto.getBankingProduct().getProductCode() == 7) {
							String msgError = "El cliente Persona VIP No puede puede tener asignado: "
									+ clienteProducto.getBankingProduct().getDescription();
							return Mono.error(new InterruptedException(msgError));
						}
						return customerProductRepo.save(generateAccountNumberAndBalance(clienteProducto));
						// SI EL CLIENTE ES PYME
					} else if (clienteProducto.getCustomer().getCustomerType().getCustomerTypeCode() == 4) {
						// SI EL PRODUCTO B. ES:
						if (clienteProducto.getBankingProduct().getProductCode() != 6) {
							String msgError = "El cliente Empresarial PYME No puede puede tener asignado: "
									+ clienteProducto.getBankingProduct().getDescription();
							return Mono.error(new InterruptedException(msgError));
						}
						return customerProductRepo.save(generateAccountNumberAndBalance(clienteProducto));
					} else if (clienteProducto.getCustomer().getCustomerType().getCustomerTypeCode() == 5) {
						// SI EL PRODUCTO B. ES:
						if (clienteProducto.getBankingProduct().getProductCode() != 7) {
							String msgError = "El cliente Empresarial CORPORATIVE No puede puede tener asignado: "
									+ clienteProducto.getBankingProduct().getDescription();
							return Mono.error(new InterruptedException(msgError));
						}
						return customerProductRepo.save(generateAccountNumberAndBalance(clienteProducto));
					} else {
						return customerProductRepo.save(generateAccountNumberAndBalance(clienteProducto));

					}
				});
	}

	public CustomerBankingProduct generateAccountNumberAndBalance(CustomerBankingProduct clienteProducto) {
		clienteProducto.setAccountNumber(clienteProducto.generarNumeroCuenta("999", 10));
		clienteProducto.setAccountNumberCCI(clienteProducto.generarNumeroCuenta("666", 20));
		clienteProducto.setPass(clienteProducto.generarNumeroCuenta("0", 4));
		clienteProducto.setBalance(0.00);
		return clienteProducto;
	}
}
