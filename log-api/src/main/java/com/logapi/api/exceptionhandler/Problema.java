// essa classe representa um erro
package com.logapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL) // não inclui propriedades nula
@Getter
@Setter
public class Problema {

	private Integer status;
	private LocalDateTime dataHora; // hora que foi gerado o problema
	private String titulo;
	private List<Campo> campos;
	
	@AllArgsConstructor // constructor
	@Getter
	public static class Campo { 
		// esse classe vai ter um constructor, que recebe nome e mensagem
		private String nome;
		private String mensagem;
	}
}
