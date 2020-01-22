package com.operationbanking.app.models;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BankingProductType {
	private String idProductType;

	@NotEmpty
	private String description;

	public BankingProductType(@NotEmpty String description) {
		super();
		this.description = description;
	}

}
