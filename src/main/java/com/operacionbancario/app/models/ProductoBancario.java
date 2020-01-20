package com.operacionbancario.app.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductoBancario {
	@Id
	private String idProducto;

	@NotEmpty
	private String descripcion;

	@NotEmpty
	private Integer numMaxDeposito;

	@NotEmpty
	private Integer numMaxRetiro;
	@Valid
	private TipoProducto tipoProducto;
	
	@NotEmpty
	private Double comision;
	@NotEmpty
	private Integer codigoProducto;
}
