package com.operationbanking.app.business;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.operationbanking.app.consolidado.BankDescription;
import com.operationbanking.app.consolidado.ReporteConsolidadoDTO;
import com.operationbanking.app.dto.Bank;
import com.operationbanking.app.dto.BankingProduct;
import com.operationbanking.app.dto.Customer;
import com.operationbanking.app.dto.CustomerCreditProduct;
import com.operationbanking.app.dto.ReporteProductoSaldoDTO;
import com.operationbanking.app.models.CustomerBankingProduct;
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
		return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-clientes/customers/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Customer.class).log()
				.flatMapMany(cli -> {
					return customerProductRepo.findByCustomer(cli);
				}).flatMap(clPro -> {
					return Flux.just(new ReporteProductoSaldoDTO(clPro.getBankingProduct().getDescription(),
							clPro.getBalance()));
				});
	}

	@Override
	public Flux<CustomerBankingProduct> findByCliente(String idCliente) {
		return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-clientes/customers/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Customer.class).log().flatMapMany(cli -> {
					return customerProductRepo.findByCustomer(cli);
				});
	}

	@Override
	public Mono<CustomerBankingProduct> saveClienteProductoBancario(CustomerBankingProduct clienteProducto) {
		return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-clientes/customers/").build().get()
				.uri(clienteProducto.getCustomer().getIdCustomer()).retrieve().bodyToMono(Customer.class).log()
				.flatMap(cl -> {
					clienteProducto.setCustomer(cl);
					System.out.println("[API DEUDAS INICIO - CLIENTE]: " + cl);
					return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-operacionescreditos/customers-products/deudas/").build().get()
							.uri(cl.getDni()).retrieve().bodyToFlux(CustomerCreditProduct.class).log().count();	
				}).flatMap(countDeudas ->{
					
					System.out.println("[API DEUDAS FIN - COUNT]: " + countDeudas);

					if(countDeudas > 0) {
						return Mono.error(new RuntimeException("ERROR. El cliente: " +clienteProducto.getCustomer().getFirstName()+ " " + clienteProducto.getCustomer().getLastnamePaternal() + ". DNI: " + clienteProducto.getCustomer().getDni() + " TIENE POR LO MENOS UNA DEUDA CREDITO"));
					}
					System.out.println("[API DEUDAS FIN - ELSE]: " + countDeudas);
					return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-bancario/products/").build()
							.get().uri(clienteProducto.getBankingProduct().getIdProduct()).retrieve()
							.bodyToMono(BankingProduct.class).log();
				})
				.flatMap(p -> {
					System.out.println("[BUSCA BANCO INI]: " + p);
					clienteProducto.setBankingProduct(p);
					return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/bank/").build().get()
							.uri(clienteProducto.getBank().getIdBank()).retrieve().bodyToMono(Bank.class).log();
				}).flatMap(bank -> {
					clienteProducto.setBank(bank);
					System.out.println("[BUSCA BANCO FIN]: " + bank);

					if (clienteProducto.getCustomer().getBank().getCodeBank() == clienteProducto.getBankingProduct()
							.getBank().getCodeBank()
							&& clienteProducto.getBankingProduct().getBank().getCodeBank() == clienteProducto.getBank().getCodeBank()) {
						System.out.println("[BANCOS IGUALES]");
						return customerProductRepo
								.findByCustomer_IdCustomerAndCustomer_CustomerType_CustomerTypeCodeAndBankingProduct_ProductCodeAndBank_CodeBank(clienteProducto.getCustomer().getIdCustomer(),
										clienteProducto.getCustomer().getCustomerType().getCustomerTypeCode(),
										clienteProducto.getBankingProduct().getProductCode(),
										clienteProducto.getBank().getCodeBank())
								.doOnNext(clPro -> System.out.println("[customerProductRepo]: " + clPro)).count();
					}
					System.out.println("[BANCOS DIFERENTES]: " + clienteProducto.getCustomer().getBank().getCodeBank() + " " + clienteProducto.getBankingProduct()
					.getBank().getCodeBank() + " " + clienteProducto.getBank().getCodeBank());
					return Mono.error(new RuntimeException("Los bancos asociados deben ser iguales"));
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


	@Override
	public Mono<ReporteConsolidadoDTO> reporteConsolidadov2(String idCliente) {
		ReporteConsolidadoDTO dto = new ReporteConsolidadoDTO();
		dto.setLstDescription(new ArrayList<>());

		return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-clientes/customers/").build().get()
		.uri(idCliente).retrieve().bodyToMono(Customer.class).log()
		.flatMapMany(cliente-> {
			dto.setClienteNombresApellidos(cliente.getFirstName() + " " + cliente.getLastnamePaternal() + " " + cliente.getLastnameMaternal());
			dto.setDni(cliente.getDni());
			return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/bank/").build().get()
					.retrieve().bodyToFlux(Bank.class).log();
		}).flatMap(banco -> {
			return customerProductRepo.findByCustomer_DniAndBank_CodeBank(dto.getDni(), banco.getCodeBank());
		}).map(clpro -> {
			if(clpro != null) {
				dto.getLstDescription().add(new BankDescription(clpro.getBank().getDescription(), clpro.getBankingProduct().getDescription(), clpro.getCustomer().getCustomerType().getDescription()));
			}
			return clpro;
		}).then(Mono.just(dto));
	}
	@Override
	public Flux<CustomerBankingProduct> productosXDni(String dni) {
		return customerProductRepo.findByCustomer_Dni(dni);
	}

	@Override
	public Flux<CustomerBankingProduct> productosXCodigoBanco(Integer codeBank) {
		return customerProductRepo.findByBank_CodeBank(codeBank);
	}

	@Override
	public Mono<CustomerBankingProduct> buscarPorNumeroCuenta(String accountNumber) {
		return customerProductRepo.findByAccountNumber(accountNumber);

	}
}
