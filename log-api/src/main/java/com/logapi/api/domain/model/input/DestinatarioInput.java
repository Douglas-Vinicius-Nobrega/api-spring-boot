package com.logapi.api.domain.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DestinatarioInput {
	@NotBlank // campo obrigatório
	private String nome;
	
	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String numero;
	
	@NotBlank
	private String complemento;
	
	@NotBlank
	private String bairro;
}
