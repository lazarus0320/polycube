package kr.co.polycube.users.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.polycube.backendtest.BackendTestApplication;
import kr.co.polycube.backendtest.users.vo.in.AddUserRequestVo;
import kr.co.polycube.backendtest.users.vo.in.UpdateUserRequestVo;

@SpringBootTest(classes = BackendTestApplication.class)
@AutoConfigureMockMvc
class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void addUser() throws Exception {

		AddUserRequestVo requestVo = AddUserRequestVo.builder()
				.name("test name")
				.build();

		String content = objectMapper.writeValueAsString(requestVo);

		mockMvc.perform(
				post("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.id").exists());
	}

	@Test
	void getUserDetail() throws Exception {

		Long userId = 1L;

		mockMvc.perform(
			get("/users/{id}", userId)
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.id").exists())
			.andExpect(jsonPath("$.result.name").exists());
	}

	@Test
	void getUserDetailNotFound() throws Exception {

		mockMvc.perform(get("/users/{id}", 999L))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.reason").exists());
	}

	@Test
	void updateUser() throws Exception {

		Long userId = 1L;

		UpdateUserRequestVo requestVo = UpdateUserRequestVo.builder()
			.name("Updated Name")
			.build();

		String content = objectMapper.writeValueAsString(requestVo);

		mockMvc.perform(
				put("/users/{id}", userId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.id").value(userId))
			.andExpect(jsonPath("$.result.name").value("Updated Name"));
	}

	@Test
	void updateUser_NotFound() throws Exception {

		Long userId = 999L;

		UpdateUserRequestVo requestVo = UpdateUserRequestVo.builder()
			.name("Updated Name")
			.build();

		String content = objectMapper.writeValueAsString(requestVo);

		mockMvc.perform(
				put("/users/{id}", userId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(content)
			)
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.reason").exists());
	}
}
