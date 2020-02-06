package com.operationbanking.app.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.operationbanking.app.models.TypeOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperacionBancariaDTO {
	@NotEmpty(message = "El numero de cuenta origen no puede ser nulo o estar en blanco")
	private String numeroCuentaOrigen;
	//@NotEmpty(message = "El numero de cuenta destino no puede ser nulo o estar en blanco")
	private String numeroCuentaDestino;
	//@NotEmpty(message = "El numero de cuenta CCI destino no puede ser nulo o estar en blanco")
	private String numeroCuentaCCIDestino;
	private String numeroTarjetaDestino;
	@NotNull(message = "El tipo de operacion no puede ser nulo")
	private TypeOperation typeOperation;
	@NotNull(message = "El atm no puede ser nulo")
	private Atm atm;
	@NotNull(message = "El monto no puede ser nulo")
	private double monto;
	
	private double interes;
	@JsonIgnore
	private Long count;
}
