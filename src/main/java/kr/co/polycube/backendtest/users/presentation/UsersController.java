package kr.co.polycube.backendtest.users.presentation;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 API", description = "유저 관련 API endpoints")
@RequestMapping("/users")
public class UsersController {
	// `/users` API를 호출하면, `{"id": ?}`을 응답한다.
	// `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.

	@Operation(summary = "유저 조회", description = "유저 조회")
	@GetMapping
	public ResponseEntity<Map<String, String>> getUser() {
		return ResponseEntity.ok(Map.of("id", "?"));
	}
}
