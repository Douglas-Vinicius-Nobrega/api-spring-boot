package com.logapi.api.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.logapi.api.domain.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // 
@Getter
@Setter
@Entity // Jarkata 
public class Cliente { 
	
	@NotNull(groups = ValidationGroups.ClienteId.class) // mudamos a validação do clienteId
	@EqualsAndHashCode.Include // apenas o ID vai ser gerado os metodos Equalw e hashcode
	@Id // identifica a entidade, estamos indentificando a chave primaria dessa classe
	@GeneratedValue(strategy = GenerationType.IDENTITY) // usando a forma nativa do db
	private Long id;
	
	@NotBlank // impede que o valor seja nulo e vazio
	@Size(max = 60) // validação do tamanho máximo 60 caracteres
	private String nome;
	
	// validações
	@NotBlank
	@Email // validação para email, conferindo se tem o @
	@Size(max = 255)
	private String email;
	
	@NotBlank
	@Size(max = 20)
	@Column(name = "fone") // no meu db está com fone e aqui telefone
	private String telefone;
}