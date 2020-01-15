package com.operacionbancario.app.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.operacionbancario.app.models.CuentaBancaria;
import com.operacionbancario.app.models.MovimientoBancario;
import com.operacionbancario.app.models.OperacionBancariaDTO;
import com.operacionbancario.app.repository.ICuentaBancariaRepo;
import com.operacionbancario.app.repository.IMovimientoRepo;

import reactor.core.publisher.Mono;

@Service
public class OperacionesServiceImpl implements IOperacionesService{

	@Autowired
	private ICuentaBancariaRepo cuentaRepo;
	
	@Autowired 
	private IMovimientoRepo repo;
	
	@Override
	public Mono<CuentaBancaria> retiro(OperacionBancariaDTO dto) {
		return cuentaRepo.findById(dto.getCuentaBancaria().getIdCuentaFinanciera())
		.flatMap(c -> {
			MovimientoBancario mov = new MovimientoBancario(c, c.getCliente(), dto.getMonto(), "Retiro", new Date());
			if(c.getSaldo() > dto.getMonto()) {
				c.setSaldo(c.getSaldo()-dto.getMonto());
				repo.save(mov).subscribe();
				 return cuentaRepo.save(c);
			}
			return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
		});
	}

	@Override
	public Mono<CuentaBancaria> deposito(OperacionBancariaDTO dto) {
		return cuentaRepo.findById(dto.getCuentaBancaria().getIdCuentaFinanciera())
				.flatMap(c -> {
					MovimientoBancario mov = new MovimientoBancario(c,c.getCliente(), dto.getMonto(), "Deposito", new Date());
					c.setSaldo(c.getSaldo() + dto.getMonto());
					repo.save(mov).subscribe();
					return cuentaRepo.save(c);
				});
	}

}
