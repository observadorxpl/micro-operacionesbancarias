package com.operationbanking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReporteComisionesDTO {
	private String numeroCuentaOrigen;
	private String tipoOperacion;
	private Double monto;
	private Double interes;
	
	
}
