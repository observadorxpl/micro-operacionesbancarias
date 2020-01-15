package com.operacionbancario.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Cliente {
	private String idCliente;

	private String nombreCompleto;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String dni;

	private TipoCliente tipoCliente;

	public Cliente(String nombreCompleto, String apellidoPaterno, String apellidoMaterno, String dni,
			TipoCliente tipoCliente) {
		super();
		this.nombreCompleto = nombreCompleto;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.dni = dni;
		this.tipoCliente = tipoCliente;
	}
	
	
	
	



	
}
