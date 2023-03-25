// Classe que representa e entidade entrega

package com.logapi.api.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.logapi.api.domain.ValidationGroups;
import com.logapi.api.domain.exception.NegocioException;

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
	@NotNull
	@Valid // valida o objeto cliente
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class) // mudando a validação de default para validationgroup
	private Cliente cliente; // associação com a entidade cliente
	
	@Valid
	@Embedded 
	private Destinatario destinatario; // associação com a entidade Destinatario

	@NotNull
	private BigDecimal taxa;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL) // propriedade dona do relacionamento
	private List<Ocorrencia> ocorrencias = new ArrayList<>();
	
	@JsonProperty(access = Access.READ_ONLY) // apenas leitura, para o consumidor da api
	@Enumerated(EnumType.STRING)
	private StatusEntrega status; // associação com a entidade StatusEntrega
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataPedido;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.getOcorrencias().add(ocorrencia);
		
		return ocorrencia;
	}

	public void finalizar() {
		if(naoPodeSerFinalizada()) {
			throw new NegocioException("Entrega não pode ser finalizada");
		}
		setStatus(StatusEntrega.FINALIZADO);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
}
