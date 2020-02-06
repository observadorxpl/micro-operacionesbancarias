package com.operationbanking.app.models;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.operationbanking.app.dto.Atm;
import com.operationbanking.app.dto.CustomerCreditProduct;

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
	private CustomerCreditProduct customerCreditProduct;
	private double amount;
	private double interests;
	@NotEmpty // RETIRO, DEPOSITO, ETC
	private TypeOperation typeOperation;
	private Atm atm;
	@NotEmpty
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date movementDate;
	// Movimiento para transacciones bancarias
	public BankingMovement(String accountNumberOrigin, CustomerBankingProduct customerBankingProduct, double amount,
			double interests, @NotEmpty TypeOperation typeOperation, Atm atm, @NotEmpty Date movementDate) {
		super();
		this.accountNumberOrigin = accountNumberOrigin;
		this.customerBankingProduct = customerBankingProduct;
		this.amount = amount;
		this.interests = interests;
		this.typeOperation = typeOperation;
		this.atm = atm;
		this.movementDate = movementDate;
	}

	// Movimiento para un pago a tarjeta de credito
	public BankingMovement(String accountNumberOrigin, CustomerCreditProduct customerCreditProduct, double amount,
			double interests, @NotEmpty TypeOperation typeOperation, Atm atm, @NotEmpty Date movementDate) {
		super();
		this.accountNumberOrigin = accountNumberOrigin;
		this.customerCreditProduct = customerCreditProduct;
		this.amount = amount;
		this.interests = interests;
		this.typeOperation = typeOperation;
		this.atm = atm;
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

	public String getIdMovement() {
		return idMovement;
	}

	public void setIdMovement(String idMovement) {
		this.idMovement = idMovement;
	}

	public TypeOperation getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(TypeOperation typeOperation) {
		this.typeOperation = typeOperation;
	}

	public Atm getAtm() {
		return atm;
	}

	public void setAtm(Atm atm) {
		this.atm = atm;
	}

	public CustomerCreditProduct getCustomerCreditProduct() {
		return customerCreditProduct;
	}

	public void setCustomerCreditProduct(CustomerCreditProduct customerCreditProduct) {
		this.customerCreditProduct = customerCreditProduct;
	}
	
}
