package com.operationbanking.app.models;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
	@Id
	private String idBank;
	@NotEmpty(message = "La descripcion del banco no puede estar vacio")
	private String description;
	@NotEmpty(message = "El codigo del tipo de banco no puede estar vacio")
	private Integer codeBank;
	@NotEmpty(message = "La direccion del banco no puede estar vacio")
	private String address;
}
