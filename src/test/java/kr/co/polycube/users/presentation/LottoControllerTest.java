package kr.co.polycube.users.presentation;

import static org.hamcrest.Matchers.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import kr.co.polycube.backendtest.BackendTestApplication;
import kr.co.polycube.backendtest.lotto.domain.Lotto;
import kr.co.polycube.backendtest.lotto.dto.out.AddLottoResponseDto;
import kr.co.polycube.backendtest.lotto.infrastructure.LottoRepository;

@SpringBootTest(classes = BackendTestApplication.class)
@AutoConfigureMockMvc
@Transactional
class LottoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private LottoRepository lottoRepository;

	@Test
	void addLotto_IntegrationTest() throws Exception {

		// 테스트 전 로또 개수 확인
		long initialCount = lottoRepository.count();

		String responseBody = mockMvc.perform(
				post("/lottos")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result.numbers").isArray())
			.andExpect(jsonPath("$.result.numbers.length()").value(6))
			.andExpect(jsonPath("$.result.numbers[*]").value(
				allOf(
					everyItem(greaterThanOrEqualTo(1)),
					everyItem(lessThanOrEqualTo(45))
				)
			))
			.andReturn()
			.getResponse()
			.getContentAsString();

		// 실제 DB에 로또가 저장되었는지 확인
		long afterCount = lottoRepository.count();
		assertEquals(initialCount + 1, afterCount, "로또가 DB에 저장되어야 합니다.");

		// 응답된 로또 번호와 DB에 저장된 로또 번호 일치 확인
		AddLottoResponseDto responseDto = objectMapper.readValue(
			objectMapper.readTree(responseBody).path("result").toString(),
			AddLottoResponseDto.class
		);

		Lotto savedLotto = lottoRepository.findAll().get(lottoRepository.findAll().size() - 1);

		assertEquals(responseDto.getNumbers().get(0), savedLotto.getNumber1());
		assertEquals(responseDto.getNumbers().get(1), savedLotto.getNumber2());
		assertEquals(responseDto.getNumbers().get(2), savedLotto.getNumber3());
		assertEquals(responseDto.getNumbers().get(3), savedLotto.getNumber4());
		assertEquals(responseDto.getNumbers().get(4), savedLotto.getNumber5());
		assertEquals(responseDto.getNumbers().get(5), savedLotto.getNumber6());
	}
}
