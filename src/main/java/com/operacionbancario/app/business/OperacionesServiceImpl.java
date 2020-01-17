package com.operacionbancario.app.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.operacionbancario.app.models.MovimientoBancario;
import com.operacionbancario.app.models.OperacionBancariaDTO;
import com.operacionbancario.app.repository.IClienteProductoRepository;
import com.operacionbancario.app.repository.IMovimientoRepo;

import reactor.core.publisher.Mono;

@Service
public class OperacionesServiceImpl implements IOperacionesService{

	@Autowired
	private IClienteProductoRepository clienteProductoRepo;
	@Autowired 
	private IMovimientoRepo repo;
	
	
	public Mono<MovimientoBancario> retiro(OperacionBancariaDTO dto) {
		return clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino())
		.flatMap(c -> {
			if(c.getSaldo() >= dto.getMonto()) {
				c.setSaldo(c.getSaldo() - dto.getMonto());
				return clienteProductoRepo.save(c);
			}
			return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
		}).flatMap(clPro -> {
			MovimientoBancario mov =  new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(), dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
	}
	
	@Override
	public Mono<MovimientoBancario> deposito(OperacionBancariaDTO dto) {
		return clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino())
			.flatMap(clPro -> {
				clPro.setSaldo(clPro.getSaldo() + dto.getMonto());
				return clienteProductoRepo.save(clPro);
				})
			.flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(), dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
	}
	
}
