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
}
