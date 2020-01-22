package com.operationbanking.app.models;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Customer {
	private String idCustomer;

	private String firstName;
	@NotEmpty
	private String lastnamePaternal;
	@NotEmpty
	private String lastnameMaternal;
	@NotEmpty
	private String dni;

	@Valid
	private CustomerType customerType;

	public Customer(String firstName, @NotEmpty String lastnamePaternal, @NotEmpty String lastnameMaternal,
			@NotEmpty String dni, @Valid CustomerType customerType) {
		super();
		this.firstName = firstName;
		this.lastnamePaternal = lastnamePaternal;
		this.lastnameMaternal = lastnameMaternal;
		this.dni = dni;
		this.customerType = customerType;
	}	
}
