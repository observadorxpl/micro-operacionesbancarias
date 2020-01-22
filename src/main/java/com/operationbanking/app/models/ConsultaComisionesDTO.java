package com.operationbanking.app.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsultaComisionesDTO {
	
	private String numeroCuenta;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date rangoInicio;
	
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date rangoFin;


	public String getNumeroCuenta() {
		return numeroCuenta;
	}


	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date getRangoInicio() {
		return rangoInicio;
	}


	public void setRangoInicio(Date rangoInicio) {
		this.rangoInicio = rangoInicio;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date getRangoFin() {
		return rangoFin;
	}


	public void setRangoFin(Date rangoFin) {
		this.rangoFin = rangoFin;
	}
    
    
}
