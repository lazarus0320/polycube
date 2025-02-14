package kr.co.polycube.backendtest.lotto.config;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import kr.co.polycube.backendtest.lotto.domain.Lotto;
import kr.co.polycube.backendtest.lotto.domain.Winner;
import kr.co.polycube.backendtest.lotto.infrastructure.LottoRepository;
import kr.co.polycube.backendtest.lotto.infrastructure.WinnerRepository;

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("test")
class LottoWinnerBatchConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private LottoRepository lottoRepository;

	@Autowired
	private WinnerRepository winnerRepository;

	@BeforeEach
	void setUp() {
		winnerRepository.deleteAll();
		lottoRepository.deleteAll();
	}

	@Test
	void lottoWinnerBatchTest() throws Exception {
		// given
		List<Lotto> testLottos = createTestLottos();
		lottoRepository.saveAll(testLottos);

		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("time", System.currentTimeMillis())
			.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

		List<Winner> winners = winnerRepository.findAll();
		assertThat(winners).hasSize(testLottos.size());

		// 모든 로또에 대해 당첨 결과가 생성되었는지 확인
		assertThat(winners)
			.extracting(Winner::getRank)
			.doesNotContainNull();
	}

	private List<Lotto> createTestLottos() {

		return List.of(
			Lotto.builder()
				.number1(1)
				.number2(2)
				.number3(3)
				.number4(4)
				.number5(5)
				.number6(6)
				.build(),
			Lotto.builder()
				.number1(7)
				.number2(8)
				.number3(9)
				.number4(10)
				.number5(11)
				.number6(12)
				.build(),
			Lotto.builder()
				.number1(13)
				.number2(14)
				.number3(15)
				.number4(16)
				.number5(17)
				.number6(18)
				.build()
		);
	}
}
