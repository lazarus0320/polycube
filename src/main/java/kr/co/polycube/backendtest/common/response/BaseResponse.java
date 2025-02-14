package kr.co.polycube.backendtest.common.response;

import java.util.Map;

public record BaseResponse<T>(T result) {

	// 요청 성공 -> return 객체 필요의 경우
	public BaseResponse(T result) {
		this.result = result;
	}

	// 요청 실패
	public static BaseResponse<Map<String, String>> error(String reason) {

		return new BaseResponse<>(Map.of("reason", reason));
	}
}

