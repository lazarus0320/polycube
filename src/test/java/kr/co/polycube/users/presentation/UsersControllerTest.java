package kr.co.polycube.users.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.polycube.backendtest.BackendTestApplication;
import kr.co.polycube.backendtest.users.vo.in.AddUserRequestVo;

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
				MockMvcRequestBuilders.post("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.content(content)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.id").exists());
	}
}
