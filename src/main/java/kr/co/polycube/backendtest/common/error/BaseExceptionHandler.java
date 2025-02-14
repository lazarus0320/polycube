package kr.co.polycube.backendtest.common.error;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

	static final String ERROR_KEY = "reason";

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Map<String, String>> handleBaseException(BaseException e) {

		log.error("BaseException -> {} {})", e.getStatus(), e.getStatus().getMessage(), e);

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(Map.of(ERROR_KEY, e.getStatus().getMessage()));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Map<String, String>> handleNoHandlerFoundException(NoHandlerFoundException e) {

		log.error("NoHandlerFoundException: ", e);

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(Map.of(ERROR_KEY, e.getMessage()));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {

		log.error("RuntimeException: ", e);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(Map.of(ERROR_KEY, e.getMessage()));
	}
}
