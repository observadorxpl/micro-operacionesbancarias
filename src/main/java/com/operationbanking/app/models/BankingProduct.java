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
@Getter
@Setter
@ToString
public class BankingProduct {
	private String idProduct;

	@NotEmpty
	private String description;

	@NotEmpty
	private Integer numMaxDeposit;

	@NotEmpty
	private Integer numMaxWithdrawal;
	@Valid
	private BankingProductType productType;
	
	@NotEmpty
	private Double commision;
	@NotEmpty
	private Integer productCode;
	
	@Valid
	private Bank bank;

	public BankingProduct(@NotEmpty String description, @NotEmpty Integer numMaxDeposit,
			@NotEmpty Integer numMaxWithdrawal, @Valid BankingProductType productType, @NotEmpty Double commision,
			@NotEmpty Integer productCode, @Valid Bank bank) {
		super();
		this.description = description;
		this.numMaxDeposit = numMaxDeposit;
		this.numMaxWithdrawal = numMaxWithdrawal;
		this.productType = productType;
		this.commision = commision;
		this.productCode = productCode;
		this.bank = bank;
	}
}
