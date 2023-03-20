// Classe que representa e entidade entrega

package com.logapi.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // hashcode e equals apenas para o ID
@Entity // transformando em uma entidade para o Jakarta persisten essa classe
public class Entrega {
	
	@EqualsAndHashCode.Include
	@Id // chave primaria dessa entidade
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto incremento
	private Long id;
	
	@ManyToOne // relacionamento entre entrega e cliente, dizendo que é N(muitos) para 1
	// muitas entregas para um cliente
	private Cliente cliente; // associação com a entidade cliente
	
	@Embedded 
	private Destinatario destinatario; // associação com a entidade Destinatario

	private BigDecimal taxa;
	
	@JsonProperty(access = Access.READ_ONLY) // apenas leitura, para o consumidor da api
	@Enumerated(EnumType.STRING)
	private StatusEntrega status; // associação com a entidade StatusEntrega
	
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime dataPedido;
	
	@JsonProperty(access = Access.READ_ONLY)
	private LocalDateTime dataFinalizacao;
	
	
}
