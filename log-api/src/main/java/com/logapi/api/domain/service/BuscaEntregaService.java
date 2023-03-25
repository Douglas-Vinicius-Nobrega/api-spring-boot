package com.logapi.api.domain.service;

import org.springframework.stereotype.Service;

import com.logapi.api.domain.exception.EntidadeNaoencontradaException;
import com.logapi.api.domain.model.Entrega;
import com.logapi.api.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BuscaEntregaService {

	private EntregaRepository entregaRepository;
	
	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new EntidadeNaoencontradaException("Entrega não encontrada"));
	}
	
}
