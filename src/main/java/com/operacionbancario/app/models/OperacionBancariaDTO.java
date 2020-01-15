package com.operacionbancario.app.models;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperacionBancariaDTO {
	@NotNull
	private CuentaBancaria cuentaBancaria;
	@NotNull
	private double monto;
}
