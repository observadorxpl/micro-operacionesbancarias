package com.operationbanking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Atm {
	private String idAtm;
	private Bank bank;
	private Integer codeAtm;
	private String address;
}
