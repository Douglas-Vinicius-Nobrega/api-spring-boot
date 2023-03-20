package com.logapi.api.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logapi.api.domain.model.Cliente;

@Repository // é um componente do spring é um repositório
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	List<Cliente> findByNome(String nome); // implementando uma pesquisa por nome
	List<Cliente> findByNomeContaining(String nome);
	// optional, pode ter um email já cadastrado ou não
	Optional<Cliente> findByEmail(String email);
}
