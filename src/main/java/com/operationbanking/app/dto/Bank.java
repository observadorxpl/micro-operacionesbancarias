package com.operationbanking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
	private String idBank;
	private String description;
	private Integer codeBank;
	private String address;
}
