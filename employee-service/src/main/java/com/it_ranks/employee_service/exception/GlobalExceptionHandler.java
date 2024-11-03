package com.it_ranks.employee_service.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	@Autowired
	private MessageSource messageSource;
	@ExceptionHandler({ConstraintViolationException.class , MethodArgumentNotValidException.class})
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		String messageHeader = "messageHeader";
		String messageKey = ex.getConstraintViolations()
				.stream()
				.map(violation -> violation.getMessage())
				.findFirst()
				.orElse("Validation error occurred");
		Locale locale = LocaleContextHolder.getLocale();
		String message_locale = messageSource.getMessage(messageHeader, null, locale)
				+ messageSource.getMessage(messageKey, null, locale);
		return new ResponseEntity<>(message_locale, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex, WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", System.currentTimeMillis());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "Data Integrity Violation");
		String exceptionMessage = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

		if (exceptionMessage.contains("NATIONAL_ID")) {
			body.put("message", "The National ID already exists. Please provide a unique National ID.");
		} else if (exceptionMessage.contains("branch.notFound")) {
			body.put("message", "The specified branch does not exist. Please provide a valid branch ID.");
		} else {
			body.put("message", "A data integrity violation occurred.");
		}
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}
	/*@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An unexpected error occurred: " + ex.getMessage());
	}*/
}