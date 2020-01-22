package com.operationbanking.app.models;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankingMovement {
	@Id
	private String idMovement;
	
	private String accountNumberOrigin;
	
	private CustomerBankingProduct customerBankingProduct;
	
	private double amount;
	
	private double interests;
	
	@NotEmpty // RETIRO, DEPOSITO, ETC
	private String operationType;
	
	@NotEmpty
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date movementDate;

	public BankingMovement(String accountNumberOrigin, CustomerBankingProduct customerBankingProduct, double amount,
			double interests, @NotEmpty String operationType, @NotEmpty Date movementDate) {
		super();
		this.accountNumberOrigin = accountNumberOrigin;
		this.customerBankingProduct = customerBankingProduct;
		this.amount = amount;
		this.interests = interests;
		this.operationType = operationType;
		this.movementDate = movementDate;
	}

	public String getAccountNumberOrigin() {
		return accountNumberOrigin;
	}


	public void setAccountNumberOrigin(String accountNumberOrigin) {
		this.accountNumberOrigin = accountNumberOrigin;
	}


	public CustomerBankingProduct getCustomerBankingProduct() {
		return customerBankingProduct;
	}


	public void setCustomerBankingProduct(CustomerBankingProduct customerBankingProduct) {
		this.customerBankingProduct = customerBankingProduct;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public double getInterests() {
		return interests;
	}


	public void setInterests(double interests) {
		this.interests = interests;
	}


	public String getOperationType() {
		return operationType;
	}


	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}


}

