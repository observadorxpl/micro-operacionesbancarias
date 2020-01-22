package com.operationbanking.app.models;

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
public class CustomerType {
	private String idCustomerType;
	
	@NotEmpty
	private String description;

	@NotEmpty
	private Integer customerTypeCode;

	public CustomerType(@NotEmpty String description, @NotEmpty Integer customerTypeCode) {
		super();
		this.description = description;
		this.customerTypeCode = customerTypeCode;
	}

	

}
