package com.operacionbancario.app.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@NotEmpty
	private String tipoOperacion;
	@NotNull
	private double monto;
	
	private double interes;
	@JsonIgnore
	private Long count;
}
