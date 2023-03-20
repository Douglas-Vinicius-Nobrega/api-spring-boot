// exceção de negócio

package com.logapi.api.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
		super(message); // passa a mensagem para a classe Pai
	}
}
