// classe respondeu por fazer o mapeamento, fazer a montagemd de objetos
// converter um tipo para outro

package com.logapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.logapi.api.domain.model.Entrega;
import com.logapi.api.domain.model.input.EntregaInput;
import com.logapi.api.model.EntregaModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component // tipo que vai ser gerenciado pelo spring
public class EntregaAssembler {

	private  ModelMapper modelMapper;
	
	public EntregaModel toModel(Entrega entrega) {
		return modelMapper.map(entrega, EntregaModel.class);
	}
	
	
	public List<EntregaModel> toCollectionModel(List<Entrega> entregas){
		return entregas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Entrega toEntity(EntregaInput entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
}
