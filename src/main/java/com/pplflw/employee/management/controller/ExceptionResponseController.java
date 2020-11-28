/**
 * 
 */
package com.pplflw.employee.management.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pplflw.employee.management.exception.BusinessException;
import com.pplflw.employee.management.exception.ExceptionResponse;
import com.pplflw.employee.management.exception.ResourceAlreadyExistException;
import com.pplflw.employee.management.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionResponseController extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionResponseController.class);

	@Autowired
	private MessageSource messages;

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Object> handleResourceNotFound(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ExceptionResponse handleBusinessException(BusinessException e) {
//		logger.error("[user = {} ] : {} ", userService.getCurrentAuthenticatedUser().getUsername(), e.getMessage());
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), null);
		return exceptionResponse;
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	public final ResponseEntity<Object> handleResourceAlreadyExistException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String uniqueId = getUniqueId();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		String message = "";
		for (int i = 0; i < fieldErrors.size(); i++) {
			if (i == 0) {
				message = fieldErrors.get(i).getDefaultMessage();
			} else {
				message = message + ", " + fieldErrors.get(i).getDefaultMessage();
			}
		}

		logger.error("[{}] {}", uniqueId, message);

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message, null);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleRuntimeException(Exception e) {
		String uniqueId = getUniqueId();
		logger.error("[{}] {}", uniqueId, e.getMessage(), e);
		return new ExceptionResponse(new Date(),
				messages.getMessage("error.general", new Object[] { uniqueId }, LocaleContextHolder.getLocale()),
				e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleGenericSystemException(Exception e) {
		String uniqueId = getUniqueId();
		logger.error("[{}] {}", uniqueId, e.getMessage(), e);
		return new ExceptionResponse(new Date(),
				messages.getMessage("error.general", new Object[] { uniqueId }, LocaleContextHolder.getLocale()),
				e.getMessage());
	}

	private String getUniqueId() {
		return Long.toString((System.currentTimeMillis()));
	}

}
