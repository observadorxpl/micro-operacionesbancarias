package com.operationbanking.app.models;

import javax.validation.constraints.NotEmpty;
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
public class TypeOperation {
	@Id
	private String idTypeOperation;
	@NotEmpty(message = "La descripcion del tipo de operacion no puede estar vacio")
	private String description;
	@NotNull(message = "El codigo de operacion unico no puede ser nulo")
	private Integer codeTypeOperation;
	@NotNull
	private Double interest;
	public TypeOperation(
			@NotEmpty(message = "La descripcion del tipo de operacion no puede estar vacio") String description,
			@NotNull(message = "El codigo de operacion unico no puede ser nulo") Integer codeTypeOperation,
			@NotNull Double interest) {
		super();
		this.description = description;
		this.codeTypeOperation = codeTypeOperation;
		this.interest = interest;
	}

	


}
