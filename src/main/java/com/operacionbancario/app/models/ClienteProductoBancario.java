package com.operacionbancario.app.models;

import javax.validation.constraints.NotNull;

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
public class ClienteProductoBancario {
	@Id
	private String clienteProductosId;
	@NotNull
	private Cliente cliente;
	@NotNull
	private ProductoBancario productoBancario;
	
	public ClienteProductoBancario(@NotNull Cliente cliente, @NotNull ProductoBancario productoBancario) {
		super();
		this.cliente = cliente;
		this.productoBancario = productoBancario;
	}


}
