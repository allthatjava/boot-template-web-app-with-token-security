package brian.template.boot.web.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import brian.template.boot.web.app.exception.NotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

//	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	@ExceptionHandler(value = { IllegalArgumentException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		log.info("IllegalArgument exception");
		return handleExceptionInternal(ex, "[[" + ex.getMessage() + "]]", new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

	@ExceptionHandler(value = { NotFoundException.class })
	protected ResponseEntity<Object> handleOtherErrors(RuntimeException ex, WebRequest request) {
		log.info("Not found exception.");
		return handleExceptionInternal(ex, "Valid error:" + ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST,
				request);
	}

}
