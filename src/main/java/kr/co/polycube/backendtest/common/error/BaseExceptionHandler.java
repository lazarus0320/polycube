package kr.co.polycube.backendtest.common.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.polycube.backendtest.common.response.BaseResponse;
import kr.co.polycube.backendtest.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<BaseResponse<Void>> BaseError(BaseException e) {
		// BaseException의 BaseResponseStatus를 가져와서 BaseResponse를 만들어서 return
		BaseResponse<Void> response = new BaseResponse<>(e.getStatus());
		log.error("BaseException -> {} {})", e.getStatus(), e.getStatus().getMessage(), e);
		return new ResponseEntity<>(response, response.httpStatus());
	}

	// 런타임 에러
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<BaseResponse<Void>> handleRuntimeException(RuntimeException e) {
		// BaseException으로 잡히지 않은 에러는 해당 에러로 처리해줌
		BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
		log.error("RuntimeException: ", e);
		for (StackTraceElement s : e.getStackTrace()) {
			System.out.println(s);
		}
		return new ResponseEntity<>(response, response.httpStatus());
	}
}
