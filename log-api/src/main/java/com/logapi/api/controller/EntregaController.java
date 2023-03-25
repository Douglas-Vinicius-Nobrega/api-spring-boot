package com.logapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logapi.api.assembler.EntregaAssembler;
import com.logapi.api.domain.model.Entrega;
import com.logapi.api.domain.model.input.EntregaInput;
import com.logapi.api.domain.repository.EntregaRepository;
import com.logapi.api.domain.service.FinalizacaoEntregaService;
import com.logapi.api.domain.service.SolicitacaoEntregaService;
import com.logapi.api.model.EntregaModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

	private EntregaRepository entregaRepository;
	private SolicitacaoEntregaService solicitaEntregaService;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	private EntregaAssembler entregaAssembler; // biblioteca indepedente do spring
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	// nos parametros, recebendo um Entrega e retornamos um entrega 
	public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
		Entrega entregaSolicitada = solicitaEntregaService.solicitar(novaEntrega);
		
		return entregaAssembler.toModel(entregaSolicitada);
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT) // retorna 204
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}
	
	@GetMapping
	public List<EntregaModel> listar() { // retorna lista de entrega
		return entregaAssembler.toCollectionModel(entregaRepository.findAll());
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId).
				map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))
						.orElse(ResponseEntity.notFound().build());
	}
}
