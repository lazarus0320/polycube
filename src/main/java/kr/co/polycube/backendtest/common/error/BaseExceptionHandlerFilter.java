package kr.co.polycube.backendtest.common.error;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.polycube.backendtest.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BaseExceptionHandlerFilter extends OncePerRequestFilter {

	/**
	 * filter 단에서 발생하는 에러는, 서블릿이 실행되기 전에 발생하므로 controller에서 잡지 못한다.
	 * 따라서 Error를 처리할 filter를 만들어서 사용한다.
	 */

	// Filter단에서 발생하는 exception을 처리하는 필터
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		}
		catch (BaseException e) {
			log.error("BaseException -> {}({})", e.getStatus(), e.getStatus().getMessage(), e);
			setErrorResponse(response, e);
		}
	}

	// Error를 Json으로 바꿔서 클라이언트에 전달
	private void setErrorResponse(HttpServletResponse response, BaseException be) throws IOException {

		// 직렬화 하기위한 object mapper
		ObjectMapper objectMapper = new ObjectMapper();

		// response의 contentType, 인코딩, 응답값을 정함
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		BaseResponse baseResponse = new BaseResponse(be.getStatus());

		try {
			response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}