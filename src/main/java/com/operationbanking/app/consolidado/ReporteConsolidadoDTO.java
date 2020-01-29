package com.operationbanking.app.consolidado;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReporteConsolidadoDTO {
	String clienteNombresApellidos; //!!
	String dni;
	List<BankDescription> lstDescription;
}
