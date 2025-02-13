package kr.co.polycube.users.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import kr.co.polycube.backendtest.BackendTestApplication;

@SpringBootTest(classes = BackendTestApplication.class)
@AutoConfigureMockMvc
class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getUser() throws Exception {
		mockMvc.perform(get("/users"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value("?"));
	}
}
