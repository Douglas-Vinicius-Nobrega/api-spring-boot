package com.logapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logapi.api.domain.model.Cliente;
import com.logapi.api.domain.repository.ClienteRepository;
import com.logapi.api.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController // controlador rest, o spring vai reconhecer essa classe como componente
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired // anotação do spring
	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;

	@GetMapping //requisição 
	public List<Cliente> listar() {
		// spring data jpa
		return clienteRepository.findAll(); // fazendo a consulta através do nosso repositório
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable long clienteId) { // métodos buscar um cliente
		return clienteRepository.findById(clienteId)
				.map(ResponseEntity::ok) // verificação se tem recursos, se tiver retorno 202
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		//return clienteRepository.save(cliente);
		return catalogoClienteService.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId,
			@Valid @RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
	
		cliente.setId(clienteId); // atualizar um cliente
//		cliente = clienteRepository.save(cliente);
		cliente = catalogoClienteService.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}	
	
	// utilizazmos o response, para retorna ok ou 404, caso n exista o usuário
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
			// se o cliente não existe, retorna um 404
		}
		// caso existir, deletar esse usuário
//		clienteRepository.deleteById(clienteId);
		catalogoClienteService.excluir(clienteId);
		
		// assim que excluir, retorna 404, pois para essa requisição, não vamos retorna um corpo
		return ResponseEntity.noContent().build();
	}
}
