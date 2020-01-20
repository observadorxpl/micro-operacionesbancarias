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
public class OperacionesServiceImpl implements IOperacionesService {

	@Autowired
	private IClienteProductoRepository clienteProductoRepo;
	@Autowired
	private IMovimientoRepo repo;
	
	@Override
	public Mono<MovimientoBancario> retiroV3(OperacionBancariaDTO dto) {
		return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), "retiro").count().flatMap(count -> {
			System.out.println("[COUNT RETIRO: ]" + count);
			dto.setTipoOperacion("retiro");
			dto.setCount(count);
			return clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino());
		}).flatMap(clPro -> {
				if(clPro.getSaldo() >= dto.getMonto() && dto.getCount() >= clPro.getProductoBancario().getNumMaxRetiro()){
					//dto.setMonto();
					dto.setInteres(dto.getMonto() * clPro.getProductoBancario().getComision());
					clPro.setSaldo(clPro.getSaldo() - dto.getMonto() - dto.getInteres());
					return clienteProductoRepo.save(clPro);
				}else if(clPro.getSaldo() >= dto.getMonto()) {
					//dto.setMonto(dto.getMonto() * (1 + clPro.getProductoBancario().getComision()));
					clPro.setSaldo(clPro.getSaldo() - dto.getMonto());
					return clienteProductoRepo.save(clPro);
				}else {
					return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
				}
		}).flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getInteres(), dto.getTipoOperacion(),new Date());
			return repo.save(mov);
		});
	}
	@Override	
	public Mono<MovimientoBancario> depositoV2(OperacionBancariaDTO dto) {
		return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), "deposito").count().flatMap(count -> {
			System.out.println("[COUNT DEPOSITO: ]" + count);
			dto.setTipoOperacion("deposito");
			dto.setCount(count);
			return clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino());
			}).flatMap(clPro -> {
				if(dto.getCount() >= clPro.getProductoBancario().getNumMaxDeposito()) {
					dto.setInteres(dto.getMonto() * clPro.getProductoBancario().getComision());
					clPro.setSaldo(clPro.getSaldo() + dto.getMonto() - dto.getInteres());
					return clienteProductoRepo.save(clPro);
				}
				clPro.setSaldo(clPro.getSaldo() + dto.getMonto());
				return clienteProductoRepo.save(clPro);
			}).flatMap(clPro -> {
				MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
						dto.getInteres(),dto.getTipoOperacion(), new Date());
				return repo.save(mov);
			});
			/*
			clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino()).flatMap(clPro -> {
			clPro.setSaldo(clPro.getSaldo() + dto.getMonto());
			return clienteProductoRepo.save(clPro);
		}).flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
		*/
	}
	public Mono<MovimientoBancario> retiroV2(OperacionBancariaDTO dto) {
		/*
		return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), "deposito").count().flatMap(count -> {
			dto.setCount(count);
			return clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino());
		}).flatMap(clPro -> {
			if (dto.getCount() > clPro.getProductoBancario().getNumMaxRetiro()) {
				dto.setMonto(dto.getMonto() * (1 + clPro.getProductoBancario().getComision()));
				if (clPro.getSaldo() >= dto.getMonto()) {
					clPro.setSaldo(clPro.getSaldo() - dto.getMonto());
					return clienteProductoRepo.save(clPro);
				}
				return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
			} else {
				if (clPro.getSaldo() >= dto.getMonto()) {
					clPro.setSaldo(clPro.getSaldo() - dto.getMonto());
					return clienteProductoRepo.save(clPro);
				}
				return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
			}
		}).flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
		*/
		return null;
	}

	public Mono<MovimientoBancario> retiro(OperacionBancariaDTO dto) {
		
		/*
		return clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino()).flatMap(c -> {
			if (c.getSaldo() >= dto.getMonto()) {
				c.setSaldo(c.getSaldo() - dto.getMonto());
				return clienteProductoRepo.save(c);
			}
			return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
		}).flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
		*/
		return null;
	}

	@Override
	public Mono<MovimientoBancario> deposito(OperacionBancariaDTO dto) {
		/*
		return clienteProductoRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino()).flatMap(clPro -> {
			dto.setTipoOperacion("deposito");
			clPro.setSaldo(clPro.getSaldo() + dto.getMonto());
			return clienteProductoRepo.save(clPro);
		}).flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
		*/
		return null;
	}
}
