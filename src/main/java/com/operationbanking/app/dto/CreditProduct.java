package com.operationbanking.app.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditProduct {
	private String idProduct;
	private String description;
	private Date createAt;
	private double interest;
	private Integer productCode;
	private CreditProductType productType;
	private Bank bank;

}
