package com.operacionbancario.app.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.ClienteProductoBancario;
import com.operacionbancario.app.models.ClienteProductosBancarioDTO;
import com.operacionbancario.app.models.CuentaBancaria;
import com.operacionbancario.app.models.ProductoBancario;
import com.operacionbancario.app.repository.IClienteProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteProductoServiceImpl implements IClienteProductoService {

	@Autowired
	private IClienteProductoRepository clienteProductoRepo;

	// elmiminar

	// sustituir por el servie

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
		// List<Producto> productos = new ArrayList<>();
		// Mono<Cliente> clienteMono =
		// clienteRepo.findById(t.getCliente().getIdCliente());
		/*
		 * 
		 * return ClienteProductoRepo.findByCliente(t.getCliente()) .map(cliente -> {
		 * System.out.println(cliente.toString()); return cliente.getProductos();
		 * }).flatMap(productos ->{ System.out.println("flatmap");
		 * t.getProductos().forEach(pro1 -> { System.out.println("foreach 1");
		 * productos.forEach(pro2 -> { System.out.println("foreach 2");
		 * if(pro1.getIdProducto() == pro2.getIdProducto()) { flag = true; } }); });
		 * if(flag = true) { System.out.println("flag true"); flag = false; return
		 * Mono.error(new
		 * InterruptedException("No puede asignar mas de una vez el mismo tipo de producto"
		 * )); } return ClienteProductoRepo.save(t); });
		 * 
		 * 
		 */
		/*
		 * return clienteMono .defaultIfEmpty(new Cliente()) .flatMap(c -> {
		 * if(c.getIdCliente() == null) { return Mono.error(new
		 * InterruptedException("El cliente no existe")); } return Mono.just(c);
		 * }).flatMap(cl -> ClienteProductoRepo.save(t));
		 */
		return null;
	}

	/*
	 * .flatMap(c -> { t.setCliente(c); for (int i = 0; i < t.getProductos().size();
	 * i++) { for (int j = 0; j < t.getProductos().size(); j++) { Producto pro =
	 * t.getProductos().get(j); Mono<Producto> productoMono =
	 * productoRepo.findById(pro.getIdProducto()); productoMono .flatMap(p -> {
	 * System.out.println("FlatMap"); if(p == null) {
	 * System.out.println("No existe producto **"); return Mono.error(new
	 * InterruptedException("No existe el producto")); } if(pro.getIdProducto() ==
	 * t.getProductos().get(i).getIdProducto()) { return Mono.error(new
	 * InterruptedException("No existe el producto")); } return Mono.just(p);
	 * }).subscribe(); } } t.setProductos(productos); return
	 * ClienteProductoRepo.save(t); });
	 */

	/*
	 * t.getProductos().forEach(pro -> { Mono<Producto> productoMono =
	 * productoRepo.findById(pro.getIdProducto()); productoMono .flatMap(p -> {
	 * System.out.println("FlatMap"); if(p == null) {
	 * System.out.println("No existe producto **"); return Mono.error(new
	 * InterruptedException("No existe el producto")); }
	 * if(t.getProductos().get(contador).getIdProducto()==pro.getIdProducto()) {
	 * 
	 * } if(t.getProductos().get(i).getIdProducto() == pro.getIdProducto()) {
	 * 
	 * } System.out.println(p.toString()); System.out.println("productosAdd");
	 * productos.add(p); return Mono.just(p); }).subscribe(); }); }
	 * t.setProductos(productos); return ClienteProductoRepo.save(t); });
	 */

	/*
	 * t.getProductos().forEach(p -> {
	 * 
	 * 
	 * Mono<Producto> productoMono = productoRepo.findById(p.getIdProducto())
	 * productoMono .defaultIfEmpty(new Producto()) .flatMap(p -> { if(p.g== null) {
	 * return Mono.error(new InterruptedException("El cliente no existe")); } return
	 * Mono.just(c); }) .
	 */

	/*
	 * }); for (int i = 0; i < t.getProductos().size(); i++) {
	 * t.getProductos().forEach(pro -> {
	 * if(t.getProductos().get(0).getCodigoProducto() == pro.getCodigoProducto()) {
	 * flag = 1; } }); } if(flag == 1) { flag = 0; return Mono.just(new
	 * ClienteProducto()); } return ClienteProductoRepo.save(t);
	 */

	@Override
	public Mono<Void> delete(ClienteProductoBancario t) {
		return clienteProductoRepo.delete(t);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return clienteProductoRepo.deleteById(id);
	}

	@Override
	public Mono<ClienteProductosBancarioDTO> saveClienteProductoDTO(ClienteProductosBancarioDTO dto) {
		/*
		 * if(dto.getCliente()!= null && dto.getProductosBancarios().size()> 0 &&
		 * dto.getProductosBancarios() != null) {
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/")
		 * .build()
		 * .get().uri(dto.getCliente().getIdCliente()).retrieve().bodyToMono(Cliente.
		 * class).log() .flatMapMany(cl->{ dto.setCliente(cl); return Mono.just(cl); })
		 * .subscribe(); dto.getProductosBancarios().forEach(x -> {
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-bancario/productos/"
		 * ).build()
		 * .get().uri(x.getIdProducto()).retrieve().bodyToMono(ProductoBancario.class).
		 * log() .map(p-> { ClienteProductoBancario clproducto = new
		 * ClienteProductoBancario(dto.getCliente(), p);
		 * clienteProductoRepo.save(clproducto).subscribe(); cuentaRepo.save(new
		 * CuentaBancaria(dto.getCliente())).subscribe(); return p; }).subscribe(); });
		 * } return this.findByCliente(dto.getCliente());
		 */
		return null;
	}

	@Override
	public Flux<CuentaBancaria> saveClienteProductoDTOv3(ClienteProductosBancarioDTO dto) {
		/*
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/")
		 * .build()
		 * .get().uri(dto.getCliente().getIdCliente()).retrieve().bodyToMono(Cliente.
		 * class).log() .flatMapMany(cl -> { dto.setCliente(cl); return
		 * Flux.fromIterable(dto.getProductosBancarios()); //FOREACH Flux<Producto> <---
		 * List<Producto> }).flatMap(pro -> { return
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-bancario/productos/"
		 * ).build()
		 * .get().uri(pro.getIdProducto()).retrieve().bodyToMono(ProductoBancario.class)
		 * .log(); }) .flatMap(pro -> { ClienteProductoBancario clproducto = new
		 * ClienteProductoBancario(dto.getCliente(), pro);
		 * clienteProductoRepo.save(clproducto).subscribe(); return cuentaRepo.save(new
		 * CuentaBancaria(dto.getCliente())); }).collectList().flux() .flatMap(cs -> {
		 * return cuentaRepo.findByCliente(dto.getCliente()); });
		 */
		return null;
	}

	@Override
	public Flux<CuentaBancaria> saveClienteProductoDTOv2(ClienteProductosBancarioDTO dto) {
		/*
		 * return
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/")
		 * .build().get()
		 * .uri(dto.getCliente().getIdCliente()).retrieve().bodyToMono(Cliente.class).
		 * log() // return clienteRepo.findById(dto.getCliente().getIdCliente())
		 * .flatMapMany(cl -> { dto.setCliente(cl); return
		 * Flux.fromIterable(dto.getProductosBancarios()); // FOREACH Flux<Producto>
		 * <--- List<Producto> }).flatMap(pro -> { System.out.println("[1]"); // Busca
		 * producto por id return
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-bancario/productos/"
		 * ).build().get()
		 * .uri(pro.getIdProducto()).retrieve().bodyToMono(ProductoBancario.class).log()
		 * ; // // }).flatMap(pro -> { System.out.println("[2]");
		 * 
		 * // Asocia cliente con producto y guarda ClienteProductoBancario clproducto =
		 * new ClienteProductoBancario(dto.getCliente(), pro); return
		 * clienteProductoRepo.save(clproducto); }).flatMap(clPro -> {
		 * System.out.println("Creando cuenta bancaria"); System.out.println("[3]");
		 * CuentaBancaria cuenta = new CuentaBancaria();
		 * 
		 * cuenta.setCliente(clPro.getCliente());
		 * cuenta.setNumeroCuenta(cuenta.generarNumeroCuenta("123", 9));
		 * cuenta.setClave("123"); cuenta.setSaldo(0.00); cuenta.setEstado(true);
		 * cuenta.setTipoCuenta("Basic"); return cuentaRepo.save(cuenta);
		 * }).collectList().flatMapMany(cs -> { return
		 * cuentaRepo.findByCliente(dto.getCliente()); });
		 */
		return null;
	}

	@Override
	public Flux<CuentaBancaria> saveClienteProductoDTOv4(ClienteProductosBancarioDTO dto) {
		/*
		 * return
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/")
		 * .build()
		 * .get().uri(dto.getCliente().getIdCliente()).retrieve().bodyToMono(Cliente.
		 * class).log() .flatMapMany(cl -> { System.out.println("flatMapMany [1]");
		 * dto.setCliente(cl); return Flux.fromIterable(dto.getProductosBancarios());
		 * //FOREACH Flux<Producto> <--- List<Producto> }).flatMap(pro -> {
		 * System.out.println("flatMap [2]"); // Busca producto por id return
		 * WebClient.builder().baseUrl("http://localhost:8099/micro-bancario/productos/"
		 * ).build()
		 * .get().uri(pro.getIdProducto()).retrieve().bodyToMono(ProductoBancario.class)
		 * .log(); // // }) .flatMap(pro -> { System.out.println("flatMap [4]"); return
		 * cuentaRepo.findByCliente(dto.getCliente()); }) .flatMap(cuenta -> {
		 * System.out.println("flatMap [5]"); int i = 0; int j = 0; int k = 0;
		 * if(cuenta.getTipoCuenta() == Constante.AHORRO) { i++; }else if
		 * (cuenta.getTipoCuenta() == Constante.PLAZO) { j++; }else if
		 * (cuenta.getTipoCuenta() == Constante.CORRIENTE) { k ++; } if(i > 1 || j > 1
		 * || k > 1) { return Mono.error(new
		 * InterruptedException(Constante.ERR_PERSONAL)); } return Flux.just(cuenta); })
		 * .flatMap(pro -> { System.out.println("flatMap [6]"); // Asocia cliente con
		 * producto y guarda ClienteProductoBancario clproducto = new
		 * ClienteProductoBancario(dto.getCliente(), dto.getProducto()); return
		 * clienteProductoRepo.save(clproducto); }) .flatMap(clPro-> {
		 * System.out.println("flatMap [7]"); return cuentaRepo.save(new
		 * CuentaBancaria(dto.getCliente())); }) .collectList() .flatMapMany(cs -> {
		 * System.out.println("flatMap [8]"); return
		 * cuentaRepo.findByCliente(dto.getCliente()); });
		 */
		return null;
	}

	@Override
	public Mono<ClienteProductosBancarioDTO> findByCliente(Cliente cliente) {
		return null;
		/*
		 * ClienteProductosBancarioDTO dto = new ClienteProductosBancarioDTO();
		 * List<ProductoBancario> productos = new ArrayList<>();
		 * System.out.println("Find"); return clienteProductoRepo.findByCliente(cliente)
		 * .map(clPro -> { System.out.println("clPro map: " +clPro);
		 * dto.setCliente(clPro.getCliente()); productos.add(clPro);
		 * dto.setProductosBancarios(productos); System.out.println(dto); return clPro;
		 * }) .then(Mono.just(dto));
		 */
	}

	@Override
	public Mono<ClienteProductosBancarioDTO> findByClientev2(Cliente cliente) {
		ClienteProductosBancarioDTO dto = new ClienteProductosBancarioDTO();
		List<ProductoBancario> productos = new ArrayList<>();
		return WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/").build().get()
				.uri(dto.getCliente().getIdCliente()).retrieve().bodyToMono(Cliente.class).log().flatMapMany(c -> {
					return clienteProductoRepo.findByCliente(c);
				}).flatMap(clPro -> {
					dto.setCliente(clPro.getCliente());
					productos.add(clPro.getProductoBancario());
					return Flux.just(productos);
				}).flatMap(prods -> {
					dto.setProductosBancarios(prods);
					return Mono.just(dto);
				}).next();
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
					if (clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente() == 2) {
						if (count > 0) {
							String msgError = "El cliente Personal Ya tiene asignado: "
									+ clienteProducto.getProductoBancario().getDescripcion();
							return Mono.error(new InterruptedException(msgError));
						}
						clienteProducto.setNumeroCuenta(clienteProducto.generarNumeroCuenta("999", 10));
						clienteProducto.setNumeroCuentaCCI(clienteProducto.generarNumeroCuenta("666", 20));
						clienteProducto.setClave(clienteProducto.generarNumeroCuenta("0", 4));
						clienteProducto.setSaldo(0.00);
						return clienteProductoRepo.save(clienteProducto);
					} else if (clienteProducto.getCliente().getTipoCliente().getCodigoTipoCliente() == 1) {
						if (clienteProducto.getProductoBancario().getCodigoProducto() == 1
								|| clienteProducto.getProductoBancario().getCodigoProducto() == 3) {
							String msgError = "El cliente Empresarial No puede puede tener asignado: "
									+ clienteProducto.getProductoBancario().getDescripcion();
							return Mono.error(new InterruptedException(msgError));
						}
						return clienteProductoRepo.save(clienteProducto);
					} else {
						return clienteProductoRepo.save(clienteProducto);

					}
				});
	}

	/*
	 * @Override public Mono<ClienteProductoBancario>
	 * saveClienteProductoBancario(ClienteProductoBancario clienteProducto) { return
	 * WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/")
	 * .build().get()
	 * .uri(clienteProducto.getCliente().getIdCliente()).retrieve().bodyToMono(
	 * Cliente.class).log() .flatMap(cl -> { clienteProducto.setCliente(cl); return
	 * WebClient.builder().baseUrl("http://localhost:8099/micro-bancario/productos/"
	 * ).build().get()
	 * .uri(clienteProducto.getProductoBancario().getIdProducto()).retrieve()
	 * .bodyToMono(ProductoBancario.class).log(); }).flatMap(clPro -> {
	 * clienteProducto.setProductoBancario(clPro);
	 * clienteProducto.setNumeroCuenta(clienteProducto.generarNumeroCuenta("999",
	 * 10));
	 * clienteProducto.setNumeroCuentaCCI(clienteProducto.generarNumeroCuenta("666",
	 * 20)); clienteProducto.setClave(clienteProducto.generarNumeroCuenta("0", 4));
	 * clienteProducto.setSaldo(0.00); return
	 * clienteProductoRepo.save(clienteProducto) .doOnNext(respuesta ->
	 * System.out.println(respuesta)); }); }
	 */
}
