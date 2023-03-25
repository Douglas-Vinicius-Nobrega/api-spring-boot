// classe que referencia o id do cliente

package com.logapi.api.domain.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteIdInput {
	
	@NotNull
	private Long Id;
}
