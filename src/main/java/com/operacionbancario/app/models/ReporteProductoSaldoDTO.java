package com.operacionbancario.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReporteProductoSaldoDTO {
	private String productoDescripcion;
	private Double saldo;
}
