package kr.co.polycube.backendtest.common.error;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		return ResponseEntity
			.status(404)
			.body(Map.of("reason", ex.getMessage()));
	}

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Map<String, String>> handleBaseException(BaseException ex) {
		return ResponseEntity
			.status(ex.getStatus().getHttpStatusCode().value())
			.body(Map.of("reason", ex.getStatus().getMessage()));
	}
}
