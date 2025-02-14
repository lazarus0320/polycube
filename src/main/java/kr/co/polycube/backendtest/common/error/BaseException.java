package kr.co.polycube.backendtest.common.error;

import kr.co.polycube.backendtest.common.response.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

	private final BaseResponseStatus status;

	public BaseException(BaseResponseStatus status) {
		super(status.getMessage());
		this.status = status;
	}
}
