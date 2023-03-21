// classe de serviço, onde colocamos nossa regra de negócio

package com.logapi.api.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.logapi.api.domain.exception.NegocioException;
import com.logapi.api.domain.model.Cliente;
import com.logapi.api.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {

	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId) // verifica se o cliente existe
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		boolean emailEmuso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(clienteExistente -> !clienteExistente.equals(cliente)); // retorna um optional 
				// se o cliente do repositório, for difente doq a gente está salvando
				// ai vai da match (true)
		
		if(emailEmuso) {
			throw new NegocioException("Já existe um cliente cadastrado com esse e-mail");
			// lançamos um exception com essa mensagem
		}
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
