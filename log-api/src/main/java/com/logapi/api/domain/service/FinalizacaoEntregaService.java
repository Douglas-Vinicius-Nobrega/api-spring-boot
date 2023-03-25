package com.logapi.api.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.logapi.api.domain.model.Entrega;
import com.logapi.api.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {

	private EntregaRepository entregaRepository;
	private BuscaEntregaService buscaEntregaService;
	
	@Transactional
	public void finalizar(Long entregaId) {
		// métodos buscar lança uma exception, caso a entrega não exista
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		
		entrega.finalizar();
		
		entregaRepository.save(entrega);
	}
}
