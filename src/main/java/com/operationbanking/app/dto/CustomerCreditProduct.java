package com.operationbanking.app.dto;

import com.operationbanking.app.models.Bank;
import com.operationbanking.app.models.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerCreditProduct{

	private String idCustomerCreditProduct;
	private Customer customer;
	private CreditProduct creditProduct;
	private Bank bank;
	private String cardNumber;
	private String pass;
	private Double lineCredit; 
	private Double balance;
	private boolean state;
	private Double interests;
}
