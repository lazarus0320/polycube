package kr.co.polycube.backendtest.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

	// 200: 요청 성공
	SUCCESS(HttpStatus.OK, "요청에 성공하였습니다."),

	// 400: Bad Request
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

	// 404 NOT_FOUND: 리소스를 찾을 수 없음
	NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),

	// 409 CONFLICT: 리소스의 현재 상태와 충돌
	DUPLICATED_DATA(HttpStatus.CONFLICT, "중복된 데이터입니다."),

	// 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
	;

	private final HttpStatusCode httpStatusCode;
	private final String message;
}