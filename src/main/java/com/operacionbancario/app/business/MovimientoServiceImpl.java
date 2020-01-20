package com.operacionbancario.app.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.operacionbancario.app.models.Cliente;
import com.operacionbancario.app.models.ConsultaComisionesDTO;
import com.operacionbancario.app.models.MovimientoBancario;
import com.operacionbancario.app.models.ReporteComisionesDTO;
import com.operacionbancario.app.repository.IMovimientoRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovimientoServiceImpl implements IMovimientoService {
	@Autowired
	private IMovimientoRepo movimientoRepo;

	@Override
	public Flux<MovimientoBancario> findAll() {
		return null;
	}

	@Override
	public Mono<MovimientoBancario> finById(String id) {
		return null;
	}

	@Override
	public Mono<MovimientoBancario> save(MovimientoBancario t) {
		return movimientoRepo.save(t);
	}

	@Override
	public Mono<Void> delete(MovimientoBancario t) {
		return null;
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return null;
	}

	@Override
	public Flux<MovimientoBancario> listarMovimientosCliente(String idCliente) {
		return WebClient.builder().baseUrl("http://localhost:8099/micro-clientes/clientes/").build().get()
				.uri(idCliente).retrieve().bodyToMono(Cliente.class).log().flux().defaultIfEmpty(new Cliente())
				.flatMap(c -> {
					if (c.getIdCliente() == null) {
						return Mono.error(new InterruptedException("El cliente no existe"));
					}
					return Flux.just(c);
				}).flatMap(c -> {
					return movimientoRepo.buscarPorIdCliente(c.getIdCliente());
				});
	}

	@Override
	public Flux<ReporteComisionesDTO> listarMovimientosPorRangoFecha(ConsultaComisionesDTO dto) {
		return movimientoRepo.buscarPorNumeroCuentaYRangoFechas(dto.getNumeroCuenta(), dto.getRangoInicio(),
				dto.getRangoFin(), 0.00).flatMap(mov -> {
					System.out.println("[flatMap MOV]: " + mov);
					return Flux.just(new ReporteComisionesDTO(mov.getNumeroCuentaOrigen(),
							mov.getTipoOperacion(), mov.getMonto(), mov.getInteres()));
				});
	}

	@Override
	public Flux<MovimientoBancario> buscarPorRangoFechas(ConsultaComisionesDTO dto) {
		return movimientoRepo.buscarPorRangoFechas(dto.getRangoInicio(), dto.getRangoFin());
	}

}
