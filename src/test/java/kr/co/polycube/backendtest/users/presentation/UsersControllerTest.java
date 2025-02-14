package kr.co.polycube.backendtest.users.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import kr.co.polycube.backendtest.BackendTestApplication;
import kr.co.polycube.backendtest.users.domain.Users;
import kr.co.polycube.backendtest.users.infrastructure.UsersRepository;
import kr.co.polycube.backendtest.users.vo.in.AddUserRequestVo;
import kr.co.polycube.backendtest.users.vo.in.UpdateUserRequestVo;

@SpringBootTest(classes = BackendTestApplication.class)
@AutoConfigureMockMvc
@Transactional
class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UsersRepository usersRepository;

	@Test
	void addUser() throws Exception {

		// 초기 사용자 수 확인
		long initialCount = usersRepository.count();

		AddUserRequestVo requestVo = AddUserRequestVo.builder()
			.name("test name")
			.build();

		String content = objectMapper.writeValueAsString(requestVo);

		String responseBody = mockMvc.perform(
				post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.id").exists())
			.andReturn()
			.getResponse()
			.getContentAsString();

		// DB에 사용자 추가 확인
		long afterCount = usersRepository.count();
		assertEquals(initialCount + 1, afterCount, "사용자가 DB에 저장되어야 합니다.");

		// 응답된 ID로 실제 저장된 사용자 확인
		JsonNode rootNode = objectMapper.readTree(responseBody);
		Long userId = rootNode.path("result").path("id").asLong();

		Users savedUser = usersRepository.findById(userId)
			.orElseThrow(() -> new AssertionError("저장된 사용자를 찾을 수 없습니다."));

		assertEquals("test name", savedUser.getName(), "저장된 사용자 이름이 일치해야 합니다.");
	}

	@Test
	void getUserDetail() throws Exception {

		// 기존 사용자 생성
		Users existingUser = Users.builder()
			.name("Test User")
			.build();
		Users savedUser = usersRepository.save(existingUser);

		mockMvc.perform(
				get("/users/{id}", savedUser.getId())
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.id").value(savedUser.getId()))
			.andExpect(jsonPath("$.result.name").value(savedUser.getName()));

		// 데이터베이스에서 직접 사용자 조회 검증
		Users retrievedUser = usersRepository.findById(savedUser.getId())
			.orElseThrow(() -> new AssertionError("사용자를 찾을 수 없습니다."));

		assertEquals(savedUser.getId(), retrievedUser.getId(), "조회된 사용자 ID가 일치해야 합니다.");
		assertEquals(savedUser.getName(), retrievedUser.getName(), "조회된 사용자 이름이 일치해야 합니다.");
	}

	@Test
	void updateUser() throws Exception {

		// 기존 사용자 생성
		Users existingUser = Users.builder()
			.name("Original Name")
			.build();
		Users savedUser = usersRepository.save(existingUser);

		UpdateUserRequestVo requestVo = UpdateUserRequestVo.builder()
			.name("Updated Name")
			.build();

		String content = objectMapper.writeValueAsString(requestVo);

		mockMvc.perform(
				put("/users/{id}", savedUser.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.name").value("Updated Name"));

		// DB에서 실제 업데이트 확인
		Users updatedUser = usersRepository.findById(savedUser.getId())
			.orElseThrow(() -> new AssertionError("사용자를 찾을 수 없습니다."));

		assertEquals("Updated Name", updatedUser.getName(), "사용자 이름이 업데이트되어야 합니다.");
	}

	@Test
	void updateUserWithSpecialCharacters() throws Exception {
		Users existingUser = Users.builder()
			.name("Original User")
			.build();
		Users savedUser = usersRepository.save(existingUser);

		UpdateUserRequestVo requestVo = UpdateUserRequestVo.builder()
			.name("invalid access user")
			.build();

		mockMvc.perform(
				put("/users/{id}?name=test!!", savedUser.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(requestVo))
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.reason").exists())
			// 데이터베이스의 사용자 정보가 변경되지 않았는지 확인
			.andExpect(result -> {
				Users unchangedUser = usersRepository.findById(savedUser.getId())
					.orElseThrow(() -> new AssertionError("사용자를 찾을 수 없습니다."));

				assertEquals("Original User", unchangedUser.getName(),
					"특수문자로 인한 요청에서는 사용자 정보가 변경되면 안 됩니다.");
			});
	}
}
