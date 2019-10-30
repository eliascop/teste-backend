package com.br.backend.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionGenericHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Message> lstError = this.newErrorList(ex.getBindingResult());
		return super.handleExceptionInternal(ex, lstError, headers, status, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<?> handleEmptyResultDataAccessException(RuntimeException ex, WebRequest request) {
		String userMessage = this.messageSource.getMessage("resource.not-found", null,LocaleContextHolder.getLocale());
		String devMessage = ex.toString();
		return super.handleExceptionInternal(ex, new Message(userMessage, devMessage), new HttpHeaders(),HttpStatus.NOT_FOUND, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String userMessage = this.messageSource.getMessage("resource.invalid", null,LocaleContextHolder.getLocale());
		String devMessage = ex.toString();
		return super.handleExceptionInternal(ex, new Message(userMessage, devMessage), headers, status,request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<?> handleDataIntegrityViolationException(RuntimeException ex, WebRequest request) {
		String userMessage = messageSource.getMessage("resource.not-allowed", null,LocaleContextHolder.getLocale());
		String devMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Message> errors = Arrays.asList(new Message(userMessage, devMessage));
		return super.handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<?> handleValidationGenericException(RuntimeException ex) {
		return ResponseEntity.badRequest().body(new Message(this.messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()), ex.toString()));
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
	public ResponseEntity<?> handleValidationGenericException(Exception ex) {
		return ResponseEntity.badRequest().body(new Message(this.messageSource.getMessage("bad-server", null, LocaleContextHolder.getLocale()), ex.toString()));
	}
	
	public List<Message> newErrorList(BindingResult bindingResult) {
		List<Message> errors = new ArrayList<Message>();

		for (FieldError fError : bindingResult.getFieldErrors()) {

			String userMessage = messageSource.getMessage(fError, LocaleContextHolder.getLocale());
			String devMessage = fError.toString();

			errors.add(new Message(userMessage, devMessage));
		}

		return errors;
	}
	
}