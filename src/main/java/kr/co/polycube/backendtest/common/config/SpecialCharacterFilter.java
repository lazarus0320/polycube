package kr.co.polycube.backendtest.common.config;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecialCharacterFilter extends OncePerRequestFilter {

	private static final String SPECIAL_CHARACTERS_REGEX = "[^a-zA-Z0-9/?&=:.\\_\\-]";

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String queryString = request.getQueryString();

		if (containsSpecialCharacters(requestURI) ||
			(queryString != null && containsSpecialCharacters(queryString))) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.setContentType("application/json");
			response.getWriter().write(
				"{\"reason\": \"Invalid characters in URL\"}"
			);
			return;
		}

		filterChain.doFilter(request, response);
	}

	private boolean containsSpecialCharacters(String input) {
		return input != null && input.matches(".*" + SPECIAL_CHARACTERS_REGEX + ".*");
	}
}
