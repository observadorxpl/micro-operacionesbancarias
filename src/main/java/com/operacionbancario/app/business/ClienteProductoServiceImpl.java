package com.operacionbancario.app.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.ClienteProductoBancario;
import com.operacionbancario.app.models.ProductoBancario;
import com.operacionbancario.app.models.ReporteProductoSaldoDTO;
import com.operacionbancario.app.repository.IClienteProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteProductoServiceImpl implements IClienteProductoService {

	@Autowired
	private IClienteProductoRepository clienteProductoRepo;

	@Override
	public Flux<ClienteProductoBancario> findAll() {
		return clienteProductoRepo.findAll();
	}

	@Override
	public Mono<ClienteProductoBancario> finById(String id) {
		return clienteProductoRepo.findById(id);
	}

	@Override
	public Mono<ClienteProductoBancario> save(ClienteProductoBancario t) {
		return null;
	}

	@Override
	public Mono<Void> delete(ClienteProductoBancario t) {
		return clienteProductoRepo.delete(t);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return clienteProductoRepo.deleteById(id);
	}

	@Override
	public Flux<ReporteProductoSaldoDTO> reporteProductosSaldo(String idCliente) {
		return WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Cliente.class).log().flatMapMany(cli -> {
					return clienteProductoRepo.findByCliente(cli);
				}).flatMap(clPro -> {
					return Flux.just(new ReporteProductoSaldoDTO(clPro.getProductoBancario().getDescripcion(),
							clPro.getSaldo()));
				});
	}

	@Override
	public Flux<ClienteProductoBancario> findByCliente(String idCliente) {
		return WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Cliente.class).log().flatMapMany(cli -> {
					return clienteProductoRepo.findByCliente(cli);
				});
	}

	@Override
	public Mono<ClienteProductoBancario> saveClienteProductoBancario(ClienteProductoBancario clienteProducto) {
		return WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/").build().get()
				.uri(clienteProducto.getCliente().getIdCliente()).retrieve().bodyToMono(Cliente.class).log()
				.flatMap(cl -> {
					clienteProducto.setCliente(cl);
					return WebClient.builder().baseUrl("http://localhost:8099/micro-bancario/productos/").build().get()
							.uri(clienteProducto.getProductoBancario().getIdProducto()).retrieve()
							.bodyToMono(ProductoBancario.class).log();
				}).flatMap(p -> {
					clienteProducto.setProductoBancario(p);
					return clienteProductoRepo
							.buscarPorCodigoTipoClienteIdTipoProducto(clienteProducto.getCliente().getIdCliente(),
									clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente(),
									clienteProducto.getProductoBancario().getCodigoProducto())
							.count();
				}).flatMap(count -> {
					// SI EL CLIENTE ES PERSONAL
					if (clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente() == 2) {
						if (clienteProducto.getProductoBancario().getCodigoProducto() == 1
								|| clienteProducto.getProductoBancario().getCodigoProducto() == 2
								|| clienteProducto.getProductoBancario().getCodigoProducto() == 3) {
							if (count > 0) {
								String msgError = "El cliente Personal Ya tiene asignado: "
										+ clienteProducto.getProductoBancario().getDescripcion();
								return Mono.error(new InterruptedException(msgError));
							}
							return clienteProductoRepo.save(generarNumCuentaSaldo(clienteProducto));
						}
						String msgError = "El cliente Personal No puede puede tener asignado: "
								+ clienteProducto.getProductoBancario().getDescripcion();
						return Mono.error(new InterruptedException(msgError));
						// SI EL CLIENTE ES EMPRESARIAL
					} else if (clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente() == 1) {
						// SI EL PRODUCTO B. ES: CUENTA AHORRO(1) O CUENTA A PLAZO FIJO(3)
						if (clienteProducto.getProductoBancario().getCodigoProducto() != 2) {
							String msgError = "El cliente Empresarial No puede puede tener asignado: "
									+ clienteProducto.getProductoBancario().getDescripcion();
							return Mono.error(new InterruptedException(msgError));
						}
						return clienteProductoRepo.save(generarNumCuentaSaldo(clienteProducto));
						// SI EL CLIENTE ES PERSONA VIP
					} else if (clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente() == 3) {
						// SI EL PRODUCTO B. ES: EMP. PYME(6) O EMP. CORPORATIVE(7)
						if (clienteProducto.getProductoBancario().getCodigoProducto() == 1
								|| clienteProducto.getProductoBancario().getCodigoProducto() == 2
								|| clienteProducto.getProductoBancario().getCodigoProducto() == 3
								|| clienteProducto.getProductoBancario().getCodigoProducto() == 6
								|| clienteProducto.getProductoBancario().getCodigoProducto() == 7) {
							String msgError = "El cliente Persona VIP No puede puede tener asignado: "
									+ clienteProducto.getProductoBancario().getDescripcion();
							return Mono.error(new InterruptedException(msgError));
						}
						return clienteProductoRepo.save(generarNumCuentaSaldo(clienteProducto));
						// SI EL CLIENTE ES PYME
					} else if (clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente() == 4) {
						// SI EL PRODUCTO B. ES:
						if (clienteProducto.getProductoBancario().getCodigoProducto() != 6) {
							String msgError = "El cliente Empresarial PYME No puede puede tener asignado: "
									+ clienteProducto.getProductoBancario().getDescripcion();
							return Mono.error(new InterruptedException(msgError));
						}
						return clienteProductoRepo.save(generarNumCuentaSaldo(clienteProducto));
					} else if (clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente() == 5) {
						// SI EL PRODUCTO B. ES:
						if (clienteProducto.getProductoBancario().getCodigoProducto() != 7) {
							String msgError = "El cliente Empresarial CORPORATIVE No puede puede tener asignado: "
									+ clienteProducto.getProductoBancario().getDescripcion();
							return Mono.error(new InterruptedException(msgError));
						}
						return clienteProductoRepo.save(generarNumCuentaSaldo(clienteProducto));
					} else {
						return clienteProductoRepo.save(generarNumCuentaSaldo(clienteProducto));

					}
				});
	}

	public ClienteProductoBancario generarNumCuentaSaldo(ClienteProductoBancario clienteProducto) {
		clienteProducto.setNumeroCuenta(clienteProducto.generarNumeroCuenta("999", 10));
		clienteProducto.setNumeroCuentaCCI(clienteProducto.generarNumeroCuenta("666", 20));
		clienteProducto.setClave(clienteProducto.generarNumeroCuenta("0", 4));
		clienteProducto.setSaldo(0.00);
		return clienteProducto;
	}
}
