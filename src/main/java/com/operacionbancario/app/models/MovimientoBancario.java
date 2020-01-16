package com.operacionbancario.app.models;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovimientoBancario {
	@Id
	private String IdMovimiento;
	
	private String numeroCuentaOrigen;
	
	private ClienteProductoBancario clienteProductoBancario;
	
	private double monto;
	
	@NotEmpty // RETIRO, DEPOSITO, ETC
	private String tipoOperacion;
	
	@NotEmpty
	private Date fechaMovimiento;

	public MovimientoBancario(String numeroCuentaOrigen, ClienteProductoBancario clienteProductoBancario, double monto,
			@NotEmpty String tipoOperacion, @NotEmpty Date fechaMovimiento) {
		super();
		this.numeroCuentaOrigen = numeroCuentaOrigen;
		this.clienteProductoBancario = clienteProductoBancario;
		this.monto = monto;
		this.tipoOperacion = tipoOperacion;
		this.fechaMovimiento = fechaMovimiento;
	}
}
