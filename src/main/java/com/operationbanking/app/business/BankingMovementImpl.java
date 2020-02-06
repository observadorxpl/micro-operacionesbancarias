package com.operationbanking.app.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.operationbanking.app.dto.ConsultaComisionesDTO;
import com.operationbanking.app.dto.Customer;
import com.operationbanking.app.dto.ReporteComisionesDTO;
import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.repository.IBankingMovementRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankingMovementImpl implements IBankingMovementService {

	private Logger LOG = LoggerFactory.getLogger(BankingMovementImpl.class);

	@Value("${com.bootcamp.gateway.url}")
	private String gatewayUrlPort;

	@Autowired
	private IBankingMovementRepo movimientoRepo;

	@Override
	public Flux<BankingMovement> findAll() {
		return movimientoRepo.findAll();
	}

	@Override
	public Mono<BankingMovement> finById(String id) {
		return movimientoRepo.findById(id);
	}

	@Override
	public Mono<BankingMovement> save(BankingMovement t) {
		return movimientoRepo.save(t);
	}

	@Override
	public Mono<Void> delete(BankingMovement t) {
		return movimientoRepo.delete(t);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return movimientoRepo.deleteById(id);
	}

	@Override
	public Flux<BankingMovement> listarMovimientosCliente(String idCliente) {
		return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-clientes/customers/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Customer.class).log().flux().defaultIfEmpty(new Customer())
				.flatMap(c -> {
					if (c.getIdCustomer() == null) {
						LOG.warn("El cliente los movimientos del cliente " + idCliente);
						return Mono.error(new InterruptedException("El cliente no existe"));
					}
					return Flux.just(c);
				}).flatMap(c -> movimientoRepo.buscarPorIdCliente(c.getIdCustomer()));
	}

	@Override
	public Flux<ReporteComisionesDTO> listarMovimientosPorRangoFecha(ConsultaComisionesDTO dto) {
		return movimientoRepo.buscarPorNumeroCuentaYRangoFechas(dto.getNumeroCuenta(), dto.getRangoInicio(),
				dto.getRangoFin(), 0.00).flatMap(mov -> {
					return Flux.just(new ReporteComisionesDTO(mov.getAccountNumberOrigin(),
							mov.getTypeOperation().getDescription(), mov.getAmount(), mov.getInterests()));
				});
	}

	@Override
	public Flux<BankingMovement> buscarPorRangoFechas(ConsultaComisionesDTO dto) {
		return movimientoRepo.buscarPorRangoFechas(dto.getRangoInicio(), dto.getRangoFin());
	}

}
