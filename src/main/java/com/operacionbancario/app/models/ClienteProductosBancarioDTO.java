package com.operacionbancario.app.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteProductosBancarioDTO {
	private Cliente cliente;
	private List<ProductoBancario> productosBancarios;
	@JsonIgnore
	private ProductoBancario producto;
}
