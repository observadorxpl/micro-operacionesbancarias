package com.operacionbancario.app.models;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovimientoBancario {
	@Id
	private String IdMovimiento;
	
	private String numeroCuentaOrigen;
	
	private ClienteProductoBancario clienteProductoBancario;
	
	private double monto;
	
	private double interes;
	
	@NotEmpty // RETIRO, DEPOSITO, ETC
	private String tipoOperacion;
	
	@NotEmpty
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaMovimiento;

	public MovimientoBancario(String numeroCuentaOrigen, ClienteProductoBancario clienteProductoBancario, double monto,
			double interes, @NotEmpty String tipoOperacion, @NotEmpty Date fechaMovimiento) {
		super();
		this.numeroCuentaOrigen = numeroCuentaOrigen;
		this.clienteProductoBancario = clienteProductoBancario;
		this.monto = monto;
		this.interes = interes;
		this.tipoOperacion = tipoOperacion;
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getIdMovimiento() {
		return IdMovimiento;
	}

	public void setIdMovimiento(String idMovimiento) {
		IdMovimiento = idMovimiento;
	}

	public String getNumeroCuentaOrigen() {
		return numeroCuentaOrigen;
	}

	public void setNumeroCuentaOrigen(String numeroCuentaOrigen) {
		this.numeroCuentaOrigen = numeroCuentaOrigen;
	}

	public ClienteProductoBancario getClienteProductoBancario() {
		return clienteProductoBancario;
	}

	public void setClienteProductoBancario(ClienteProductoBancario clienteProductoBancario) {
		this.clienteProductoBancario = clienteProductoBancario;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
}

