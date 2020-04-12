package com.task.util;

import java.util.Arrays;

public enum StatusEnum {
	ACTIVE(0, "ACTIVE"),
	COMPLETED(1, "COMPLETED");
	
	private Integer codigo;
	private String descricao;
	
	private StatusEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static StatusEnum getByCodigo(Integer codigo) {
		return Arrays.stream(StatusEnum.values())
				.filter(status -> status.codigo.equals(codigo))
				.findFirst().orElse(ACTIVE);
	}
	
	public static StatusEnum getByDescricao(String descricao) {
		return Arrays.stream(StatusEnum.values())
				.filter(status -> status.descricao.equals(descricao))
				.findFirst().orElse(ACTIVE);
	}
}
