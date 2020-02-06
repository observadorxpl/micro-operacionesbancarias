package com.operationbanking.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.operationbanking.app.models.TypeOperation;
import com.operationbanking.app.repository.ITypeOperationRepo;

import reactor.core.publisher.Flux;
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class OperacionesBancarioMsApplication implements CommandLineRunner{
	@Value("${com.bootcamp.gateway.url}")
	private String gatewayUrlPort;
	@Autowired
	private ITypeOperationRepo typeRepo;

	public static void main(String[] args) {
		SpringApplication.run(OperacionesBancarioMsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		TypeOperation type7 = new TypeOperation("Pago de Tarjeta Credito", 200, 0.0);
		TypeOperation type8 = new TypeOperation("Abono", 201, 0.0);
		TypeOperation type9 = new TypeOperation("Consumo", 202, 0.0);
		
		Flux.just(type7, type8, type9).flatMap(tipo -> typeRepo.save(tipo)).subscribe();
		*/
		/*
		TypeOperation type1 = new TypeOperation("Transferencia entre mis cuentas", 100, 0.0);
		TypeOperation type2 = new TypeOperation("Transferencia a cuentas del mismo banco", 101, 0.0);
		TypeOperation type3 = new TypeOperation("Transferencia a cuentas de otros bancos", 102, 0.09);
		TypeOperation type4 = new TypeOperation("Transferencia a cuentas en el extranjero", 103, 0.15);
		TypeOperation type5 = new TypeOperation("Deposito", 104, 0.0);
		TypeOperation type6 = new TypeOperation("Retiro", 105, 0.0);
		
		Flux.just(type1, type2, type3, type4, type5, type6).flatMap(tipo -> typeRepo.save(tipo)).subscribe();
		*/
		/*
		WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/bank/").build().get()
		.uri("code-bank/100").retrieve().bodyToMono(Bank.class).log().flatMap(bank -> {
			return atmRepo.save(new Atm(bank, 100, "Magdalena 1032"));
		}).flatMap(atm -> {
			return atmRepo.save(new Atm(atm.getBank(), 101, "Javier Prado 1030"));
		}).subscribe();
		
		WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/bank/").build().get()
		.uri("code-bank/101").retrieve().bodyToMono(Bank.class).log().flatMap(bank -> {
			return atmRepo.save(new Atm(bank, 102, "Jiron de la union 423"));
		}).flatMap(atm -> {
			return atmRepo.save(new Atm(atm.getBank(), 103, "Javier Prado 1030"));
		}).subscribe();
		
		WebClient.builder().baseUrl("http://" + gatewayUrlPort + "/micro-banco/bank/").build().get()
		.uri("code-bank/102").retrieve().bodyToMono(Bank.class).log().flatMap(bank -> {
			return atmRepo.save(new Atm(bank, 104, "Av arequipa 112"));
		}).flatMap(atm -> {
			return atmRepo.save(new Atm(atm.getBank(), 105, "Av Brasil 1314"));
		}).subscribe();	
		*/
	}
}
