package com.operationbanking.app.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.operationbanking.app.models.TypeOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperacionCreditoDTO {
	@NotEmpty(message = "El numero de cuenta origen no puede ser nulo o estar en blanco")
	private String numeroCuentaOrigen;
	@NotEmpty(message = "El numero de tarjeta destino no puede ser nulo o estar en blanco")
	private String numeroTarjetaDestino;
	@NotNull(message = "El tipo de operacion no puede ser nulo")
	private TypeOperation typeOperation;	
	@NotNull
	private double monto;
}
