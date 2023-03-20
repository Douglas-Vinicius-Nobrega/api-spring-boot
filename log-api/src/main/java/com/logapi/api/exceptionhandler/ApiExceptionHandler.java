package com.logapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.logapi.api.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Problema.Campo> campos = new ArrayList<>();
		
		// pegando todos o erros que foram atribuindo nessa requisição
		// cada erro, nós vamos jogar na variavel error
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField(); // 
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		Problema problema = new Problema(); // instanciando o objeto problema
		
		problema.setStatus(status.value()); // retorna o código de status http
		problema.setDataHora(LocalDateTime.now()); // retorna data e hora 
		problema.setTitulo("Um ou mais campos estão inválidos.");
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(NegocioException.class) // método por tratar essa exception
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST; // código de status 400
		
		Problema problema = new Problema();
		problema.setStatus(status.value()); // retorna o código de status http
		problema.setDataHora(LocalDateTime.now()); // retorna data e hora 
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
}
