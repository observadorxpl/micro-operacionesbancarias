package com.operationbanking.app.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.ConsultaComisionesDTO;
import com.operationbanking.app.models.Customer;
import com.operationbanking.app.models.ReporteComisionesDTO;
import com.operationbanking.app.repository.IBankingMovementRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankingMovementImpl implements IBankingMovementService {
	@Autowired
	private IBankingMovementRepo movimientoRepo;

	@Override
	public Flux<BankingMovement> findAll() {
		return null;
	}

	@Override
	public Mono<BankingMovement> finById(String id) {
		return null;
	}

	@Override
	public Mono<BankingMovement> save(BankingMovement t) {
		return movimientoRepo.save(t);
	}

	@Override
	public Mono<Void> delete(BankingMovement t) {
		return null;
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return null;
	}

	@Override
	public Flux<BankingMovement> listarMovimientosCliente(String idCliente) {
		return WebClient.builder().baseUrl("http://servicio-zuul-server:8099/micro-clientes/customers/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Customer.class).log().flux().defaultIfEmpty(new Customer())
				.flatMap(c -> {
					if (c.getIdCustomer() == null) {
						return Mono.error(new InterruptedException("El cliente no existe"));
					}
					return Flux.just(c);
				}).flatMap(c -> {
					return movimientoRepo.buscarPorIdCliente(c.getIdCustomer());
				});
	}

	@Override
	public Flux<ReporteComisionesDTO> listarMovimientosPorRangoFecha(ConsultaComisionesDTO dto) {
		return movimientoRepo.buscarPorNumeroCuentaYRangoFechas(dto.getNumeroCuenta(), dto.getRangoInicio(),
				dto.getRangoFin(), 0.00).flatMap(mov -> {
					System.out.println("[flatMap MOV]: " + mov);
					return Flux.just(new ReporteComisionesDTO(mov.getAccountNumberOrigin(),
							mov.getOperationType(), mov.getAmount(), mov.getInterests()));
				});
	}

	@Override
	public Flux<BankingMovement> buscarPorRangoFechas(ConsultaComisionesDTO dto) {
		return movimientoRepo.buscarPorRangoFechas(dto.getRangoInicio(), dto.getRangoFin());
	}

}
