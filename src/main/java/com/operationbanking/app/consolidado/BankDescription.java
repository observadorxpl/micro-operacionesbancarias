package com.operationbanking.app.consolidado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankDescription {
	String banco;
	String producto;
	String perfilCliente;
}
