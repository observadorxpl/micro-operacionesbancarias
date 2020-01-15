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
@Getter @Setter
@ToString
public class ProductoBancario {
	@Id
	private String idProducto;
	
	@NotEmpty
	private String descripcion;
	
	@NotEmpty
	private Integer codigoProducto;
	
	private String tipoProductoBancario;
	@Valid
	private TipoProducto tipoProducto;
	public ProductoBancario(@NotEmpty String descripcion, @Valid TipoProducto tipoProducto ,@NotEmpty Integer codigoProducto, String tipoProductoBancario) {
		super();
		this.descripcion = descripcion;
		this.tipoProducto = tipoProducto;
		this.codigoProducto = codigoProducto;
		this.tipoProductoBancario = tipoProductoBancario;
	}


	
}
