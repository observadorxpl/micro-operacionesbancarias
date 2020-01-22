package com.operationbanking.app.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.OperacionBancariaDTO;
import com.operationbanking.app.repository.IBankingMovementRepo;
import com.operationbanking.app.repository.ICustomerBankingProductRepository;

import reactor.core.publisher.Mono;

@Service
public class OperationServiceImpl implements IOperationService {

	@Autowired
	private ICustomerBankingProductRepository clientProductRepo;
	@Autowired
	private IBankingMovementRepo repo;
	
	@Override
	public Mono<BankingMovement> retiroV3(OperacionBancariaDTO dto) {
		return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), "retiro").count().flatMap(count -> {
			System.out.println("[COUNT RETIRO: ]" + count);
			dto.setTipoOperacion("retiro");
			dto.setCount(count);
			return clientProductRepo.findByAccountNumber(dto.getNumeroCuentaDestino());
		}).flatMap(clPro -> {
				if(clPro.getBalance() >= dto.getMonto() && dto.getCount() >= clPro.getBankingProduct().getNumMaxWithdrawal()){
					//dto.setMonto();
					dto.setInteres(dto.getMonto() * clPro.getBankingProduct().getCommision());
					clPro.setBalance(clPro.getBalance() - dto.getMonto() - dto.getInteres());
					return clientProductRepo.save(clPro);
				}else if(clPro.getBalance() >= dto.getMonto()) {
					//dto.setMonto(dto.getMonto() * (1 + clPro.getBankingProduct().getComision()));
					clPro.setBalance(clPro.getBalance() - dto.getMonto());
					return clientProductRepo.save(clPro);
				}else {
					return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
				}
		}).flatMap(clPro -> {
			BankingMovement mov = new BankingMovement(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getInteres(), dto.getTipoOperacion(),new Date());
			return repo.save(mov);
		});
	}
	@Override	
	public Mono<BankingMovement> depositoV2(OperacionBancariaDTO dto) {
		return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), "deposito").count().flatMap(count -> {
			System.out.println("[COUNT DEPOSITO: ]" + count);
			dto.setTipoOperacion("deposito");
			dto.setCount(count);
			return clientProductRepo.findByAccountNumber(dto.getNumeroCuentaDestino());
			}).flatMap(clPro -> {
				if(dto.getCount() >= clPro.getBankingProduct().getNumMaxDeposit()) {
					dto.setInteres(dto.getMonto() * clPro.getBankingProduct().getCommision());
					clPro.setBalance(clPro.getBalance() + dto.getMonto() - dto.getInteres());
					return clientProductRepo.save(clPro);
				}
				clPro.setBalance(clPro.getBalance() + dto.getMonto());
				return clientProductRepo.save(clPro);
			}).flatMap(clPro -> {
				BankingMovement mov = new BankingMovement(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
						dto.getInteres(),dto.getTipoOperacion(), new Date());
				return repo.save(mov);
			});
			/*
			clientProductRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino()).flatMap(clPro -> {
			clPro.setBalance(clPro.getBalance() + dto.getMonto());
			return clientProductRepo.save(clPro);
		}).flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
		*/
	}
	public Mono<BankingMovement> retiroV2(OperacionBancariaDTO dto) {
		/*
		return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), "deposito").count().flatMap(count -> {
			dto.setCount(count);
			return clientProductRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino());
		}).flatMap(clPro -> {
			if (dto.getCount() > clPro.getBankingProduct().getNumMaxWithdrawal()) {
				dto.setMonto(dto.getMonto() * (1 + clPro.getBankingProduct().getComision()));
				if (clPro.getBalance() >= dto.getMonto()) {
					clPro.setBalance(clPro.getBalance() - dto.getMonto());
					return clientProductRepo.save(clPro);
				}
				return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
			} else {
				if (clPro.getBalance() >= dto.getMonto()) {
					clPro.setBalance(clPro.getBalance() - dto.getMonto());
					return clientProductRepo.save(clPro);
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

	public Mono<BankingMovement> retiro(OperacionBancariaDTO dto) {
		
		/*
		return clientProductRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino()).flatMap(c -> {
			if (c.getBalance() >= dto.getMonto()) {
				c.setBalance(c.getBalance() - dto.getMonto());
				return clientProductRepo.save(c);
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
	public Mono<BankingMovement> deposito(OperacionBancariaDTO dto) {
		/*
		return clientProductRepo.findByNumeroCuenta(dto.getNumeroCuentaDestino()).flatMap(clPro -> {
			dto.setTipoOperacion("deposito");
			clPro.setBalance(clPro.getBalance() + dto.getMonto());
			return clientProductRepo.save(clPro);
		}).flatMap(clPro -> {
			MovimientoBancario mov = new MovimientoBancario(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getTipoOperacion(), new Date());
			return repo.save(mov);
		});
		*/
		return null;
	}
}
