package com.operationbanking.app.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Atm {
	@Id
	private String idAtm;
	@NotNull(message = "El banco no puede ser nulo")
	private Bank bank;
	@NotNull(message = "El codigo del tipo de banco no puede estar vacio")
	private Integer codeAtm;
	@NotEmpty(message = "La direccion del atm no puede estar vacio")
	private String address;
}
