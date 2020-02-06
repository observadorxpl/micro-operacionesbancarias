package com.operationbanking.app.business;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.operationbanking.app.dto.Atm;
import com.operationbanking.app.dto.CustomerCreditProduct;
import com.operationbanking.app.dto.OperacionBancariaDTO;
import com.operationbanking.app.models.BankingMovement;
import com.operationbanking.app.models.TypeOperation;
import com.operationbanking.app.repository.IBankingMovementRepo;
import com.operationbanking.app.repository.ICustomerBankingProductRepository;
import com.operationbanking.app.repository.ITypeOperationRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperationServiceImpl implements IOperationService {
	private Logger log = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	@Value("${com.bootcamp.gateway.url}")
	private String gatewayUrlPort;
	
	@Autowired
	private ICustomerBankingProductRepository clientProductRepo;
	@Autowired
	private IBankingMovementRepo repo;
	@Autowired
	ITypeOperationRepo typeRepo;

	// Actualiza el saldo de la cuenta destino y genera un movimiento bancario.
	// La cuenta de origen se registra como nulo
	@Override
	public Mono<BankingMovement> retiro(OperacionBancariaDTO dto) {
		return typeRepo.findById(dto.getTypeOperation().getIdTypeOperation()).flatMap(type -> {
			dto.setTypeOperation(type);
			if (dto.getTypeOperation().getCodeTypeOperation() != 105) {
				return Mono.error(new RuntimeException(
						"El tipo de operacion es incorrecta: " + dto.getTypeOperation().getDescription()));
			}
			return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/atm/").build().get()
					.uri(dto.getAtm().getIdAtm()).retrieve().bodyToMono(Atm.class).log();
					//atmRepo.findById(dto.getAtm().getIdAtm());
		}).flatMap(atm -> {
			dto.setAtm(atm);
			return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), 105).count();
		}).flatMap(count -> {
			log.info("Cantidad de retiros anteriores: " + count);
			dto.setCount(count);
			return clientProductRepo.findByAccountNumber(dto.getNumeroCuentaDestino());
		}).flatMap(clPro -> {
			if (clPro.getBalance() >= dto.getMonto()
					&& dto.getCount() >= clPro.getBankingProduct().getNumMaxWithdrawal()) {
				dto.setInteres(dto.getMonto() * clPro.getBankingProduct().getCommision());
				clPro.setBalance(clPro.getBalance() - dto.getMonto() - dto.getInteres());
				return clientProductRepo.save(clPro);
			} else if (clPro.getBalance() >= dto.getMonto()) {
				clPro.setBalance(clPro.getBalance() - dto.getMonto());
				return clientProductRepo.save(clPro);
			} else {
				return Mono.error(new InterruptedException("No tiene el saldo suficiente para retirar"));
			}
		}).flatMap(clPro -> {
			BankingMovement mov = new BankingMovement(null, clPro, dto.getMonto(), dto.getInteres(),
					dto.getTypeOperation(),dto.getAtm(), new Date());
			return repo.save(mov);
		});
	}

	// Actualiza el saldo de la cuenta destino y genera un movimiento bancario.
	// La cuenta de origen se registra como nulo
	@Override
	public Mono<BankingMovement> deposito(OperacionBancariaDTO dto) {
		return typeRepo.findById(dto.getTypeOperation().getIdTypeOperation()).flatMap(type -> {
			dto.setTypeOperation(type);
			if (dto.getTypeOperation().getCodeTypeOperation() != 104) {
				return Mono.error(new RuntimeException(
						"El tipo de operacion es incorrecta: " + dto.getTypeOperation().getDescription()));
			}
			return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/atm/").build().get()
					.uri(dto.getAtm().getIdAtm()).retrieve().bodyToMono(Atm.class).log();
					//atmRepo.findById(dto.getAtm().getIdAtm());
		}).flatMap(atm -> {
			dto.setAtm(atm);
			return repo.buscarPorNumeroCuenta(dto.getNumeroCuentaDestino(), 105).count();
		}).flatMap(count -> {
			log.info("Cantidad de depositos anteriores: " + count);
			dto.setCount(count);
			return clientProductRepo.findByAccountNumber(dto.getNumeroCuentaDestino());
		}).flatMap(clPro -> {
			dto.setInteres(dto.getMonto() * dto.getTypeOperation().getInterest());
			if (dto.getCount() >= clPro.getBankingProduct().getNumMaxDeposit()) {
				dto.setInteres((dto.getMonto() * clPro.getBankingProduct().getCommision()) + dto.getInteres());
				clPro.setBalance(clPro.getBalance() + dto.getMonto() - dto.getInteres());
				return clientProductRepo.save(clPro);
			}
			clPro.setBalance(clPro.getBalance() + dto.getMonto());
			return clientProductRepo.save(clPro);
		}).flatMap(clPro -> {
			BankingMovement mov = new BankingMovement(null, clPro, dto.getMonto(), dto.getInteres(),
					dto.getTypeOperation(),dto.getAtm(), new Date());
			return repo.save(mov);
		});
	}

	// Actualiza el saldo de la cuenta origen, la cuenta destino y genera un
	// movimiento bancario
	@Override
	public Mono<BankingMovement> transferenciaMismoBanco(OperacionBancariaDTO dto) {
		return typeRepo.findById(dto.getTypeOperation().getIdTypeOperation()).flatMap(type -> {
			dto.setTypeOperation(type);
			if (dto.getTypeOperation().getCodeTypeOperation() != 101) {
				return Mono.error(new RuntimeException(
						"El tipo de operacion es incorrecta: " + dto.getTypeOperation().getDescription()));
			}
			return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/atm/").build().get()
					.uri(dto.getAtm().getIdAtm()).retrieve().bodyToMono(Atm.class).log();
					//atmRepo.findById(dto.getAtm().getIdAtm());
		}).flatMap(atm -> {
			dto.setAtm(atm);
			return clientProductRepo.findByAccountNumber(dto.getNumeroCuentaOrigen());
		}).flatMap(clPro -> {
			dto.setInteres(dto.getMonto() * dto.getTypeOperation().getInterest());
			if (clPro.getBalance() >= dto.getMonto()) {
				clPro.setBalance(clPro.getBalance() - dto.getMonto() - dto.getInteres());
				return clientProductRepo.save(clPro);
			}
			return Mono.error(new InterruptedException("No tiene el saldo suficiente para realizar la transferencia"));
		}).flatMap(clPro -> clientProductRepo.findByAccountNumber(dto.getNumeroCuentaDestino())).flatMap(clPro -> {
			clPro.setBalance(clPro.getBalance() + dto.getMonto());
			return clientProductRepo.save(clPro);
		}).flatMap(clPro -> {
			BankingMovement mov = new BankingMovement(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getInteres(), dto.getTypeOperation(),dto.getAtm(), new Date());
			return repo.save(mov);
		});
	}

	// Actualiza el saldo de la cuenta origen, la cuenta destino CCI y genera un
	// movimiento bancario
	@Override
	public Mono<BankingMovement> transferenciaOtrosBancos(OperacionBancariaDTO dto) {
		return typeRepo.findById(dto.getTypeOperation().getIdTypeOperation()).flatMap(type -> {
			dto.setTypeOperation(type);
			if (dto.getTypeOperation().getCodeTypeOperation() != 102) {
				return Mono.error(new RuntimeException(
						"El tipo de operacion es incorrecta: " + dto.getTypeOperation().getDescription()));
			}
			if (dto.getNumeroCuentaCCIDestino() == null || dto.getNumeroCuentaCCIDestino().length() < 1) {
				return Mono.error(new RuntimeException("El numero de cuenta CCI Destino no puede ser nulo o vacio: "
						+ dto.getNumeroCuentaCCIDestino()));
			}
			return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/atm/").build().get()
					.uri(dto.getAtm().getIdAtm()).retrieve().bodyToMono(Atm.class).log();
					//atmRepo.findById(dto.getAtm().getIdAtm());
		}).flatMap(atm -> {
			dto.setAtm(atm);
			return clientProductRepo.findByAccountNumber(dto.getNumeroCuentaOrigen());
		}).flatMap(clPro -> {
			dto.setInteres(dto.getMonto() * dto.getTypeOperation().getInterest());
			if (clPro.getBalance() >= dto.getMonto()) {
				clPro.setBalance(clPro.getBalance() - dto.getMonto() - dto.getInteres());
				return clientProductRepo.save(clPro);
			}
			return Mono.error(new InterruptedException("No tiene el saldo suficiente para realizar la transferencia"));
		}).flatMap(clPro -> clientProductRepo.findByAccountNumberCCI(dto.getNumeroCuentaCCIDestino())).flatMap(clPro -> {
			clPro.setBalance(clPro.getBalance() + dto.getMonto());
			return clientProductRepo.save(clPro);
		}).flatMap(clPro -> {
			BankingMovement mov = new BankingMovement(dto.getNumeroCuentaOrigen(), clPro, dto.getMonto(),
					dto.getInteres(), dto.getTypeOperation(),dto.getAtm(), new Date());
			return repo.save(mov);
		});
	}
	@Override
	public Flux<TypeOperation> listarTipoOperaciones(){
		return typeRepo.findAll();
	}
	
	// Actualiza el saldo de la cuenta bancaria y abona al saldo de la tarjeta de credito
	// Genera un movimiento con cuenta origen (bancaria),
	// cliente-producto afectado de tipo credito
	@Transactional
	@Override
	public Mono<BankingMovement> pagoTarjetaCredito(OperacionBancariaDTO dto) {
		log.info("1. OperationServiceImpl pago-tarjeta [INICIO], OperacionBancariadto: " + dto);
			return typeRepo.findById(dto.getTypeOperation().getIdTypeOperation()).flatMap(type -> {
				dto.setTypeOperation(type);
				log.info("2. OperationServiceImpl pago-tarjeta [SET TYPEOPERACION dto]: " + dto);
				if(dto.getTypeOperation().getCodeTypeOperation() != 200) {
					return Mono.error(new RuntimeException("El tipo de operacion es incorrecta: " + dto.getTypeOperation().getDescription()));
				}
				if (dto.getNumeroTarjetaDestino() == null || dto.getNumeroTarjetaDestino().length() < 1) {
					return Mono.error(new RuntimeException("El numero tarjeta Destino no puede ser nulo o vacio: "
							+ dto.getNumeroCuentaCCIDestino()));
				}
				return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/atm/").build().get()
						.uri(dto.getAtm().getIdAtm()).retrieve().bodyToMono(Atm.class).log();
						//atmRepo.findById(dto.getAtm().getIdAtm());
			}).flatMap(atm -> {
				dto.setAtm(atm);
				log.info("3. OperationServiceImpl pago-tarjeta [SET ATM dto]: " + dto);
				return clientProductRepo.findByAccountNumber(dto.getNumeroCuentaOrigen());
			}).flatMap(clPro -> {
				log.info("4. OperationServiceImpl pago-tarjeta [INICIO], Cuenta Origen: " + clPro);
				dto.setInteres(dto.getMonto() * dto.getTypeOperation().getInterest());
				if (clPro.getBalance() >= dto.getMonto()) {
					clPro.setBalance(clPro.getBalance() - dto.getMonto() - dto.getInteres());
					log.info("4. OperationServiceImpl pago-tarjeta [ACTUALIZANDO], Cuenta Origen: " + clPro);
					return clientProductRepo.save(clPro);
				}
				return Mono.error(new InterruptedException("No tiene el saldo suficiente para realizar la transferencia"));
			}).flatMap(clPro -> {
				log.info("5. OperationServiceImpl pago-tarjeta [INICIO], WebClient Buscando Tarjeta credito: " + clPro);
				//card/{numeroTarjeta}
				return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-operacionescreditos/customers-products/card/").build().get()
				.uri(dto.getNumeroTarjetaDestino()).retrieve().bodyToMono(CustomerCreditProduct.class).log();
			}).flatMap(clProC -> {
				clProC.setBalance(clProC.getBalance() + dto.getMonto());
				log.info("6. OperationServiceImpl pago-tarjeta [ENCONTRADO], WebClient Buscando Tarjeta credito: " + clProC);
				return WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-operacionescreditos/customers-products/").build().put()
						.body(BodyInserters.fromValue(clProC)).retrieve().bodyToMono(CustomerCreditProduct.class).log(); 
			}).flatMap(clProC -> {
				log.info("7. OperationServiceImpl pago-tarjeta [ACTUALIZANDO], WebClient Buscando Tarjeta credito: " + clProC);
				BankingMovement mov = new BankingMovement(dto.getNumeroCuentaOrigen(), clProC, dto.getMonto(),
						dto.getInteres(), dto.getTypeOperation(),dto.getAtm(), new Date());
				return repo.save(mov);
			});

	}
}
