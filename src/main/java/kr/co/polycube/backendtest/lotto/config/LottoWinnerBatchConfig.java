package kr.co.polycube.backendtest.lotto.config;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import kr.co.polycube.backendtest.lotto.domain.Lotto;
import kr.co.polycube.backendtest.lotto.domain.Rank;
import kr.co.polycube.backendtest.lotto.domain.Winner;
import kr.co.polycube.backendtest.lotto.infrastructure.LottoRepository;
import kr.co.polycube.backendtest.lotto.infrastructure.WinnerRepository;

@Configuration
@EnableBatchProcessing
public class LottoWinnerBatchConfig {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private LottoRepository lottoRepository;

	@Autowired
	private WinnerRepository winnerRepository;

	@Autowired
	private JobLauncher jobLauncher;

	@Bean
	public Job lottoWinnerJob(Step lottoWinnerStep) {
		return new JobBuilder("lottoWinnerJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(lottoWinnerStep)
			.build();
	}

	@Bean
	public Step lottoWinnerStep(ItemReader<Lotto> reader, ItemProcessor<Lotto, Winner> processor, ItemWriter<Winner> writer) {
		return new StepBuilder("lottoWinnerStep", jobRepository)
			.<Lotto, Winner>chunk(10, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}

	@Bean
	public RepositoryItemReader<Lotto> lottoReader() {
		return new RepositoryItemReaderBuilder<Lotto>()
			.name("lottoReader")
			.repository(lottoRepository)
			.methodName("findAll")
			.sorts(Collections.singletonMap("id", Sort.Direction.ASC))
			.build();
	}

	@Bean
	public ItemProcessor<Lotto, Winner> lottoProcessor() {
		return lotto -> {
			List<Integer> winningNumbers = generateWinningNumbers();
			int matchCount = countMatchingNumbers(lotto, winningNumbers);
			Optional<Rank> rank = Rank.valueOfMatchCount(matchCount);

			return Winner.builder()
				.id(lotto.getId())
				.rank(rank.orElse(Rank.NONE))
				.build();
		};
	}

	@Bean
	public ItemWriter<Winner> winnerWriter() {
		return winners -> winnerRepository.saveAll(winners);
	}

	@Scheduled(cron = "0 0 0 * * SUN")
	public void runBatchJob() throws Exception {
		Job job = lottoWinnerJob(lottoWinnerStep(lottoReader(), lottoProcessor(), winnerWriter()));
		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("time", System.currentTimeMillis())
			.toJobParameters();
		jobLauncher.run(job, jobParameters);
	}

	private List<Integer> generateWinningNumbers() {
		SecureRandom secureRandom = new SecureRandom();
		return secureRandom.ints(1, 46)
			.distinct()
			.limit(6)
			.sorted()
			.boxed()
			.toList();
	}

	private int countMatchingNumbers(Lotto lotto, List<Integer> winningNumbers) {
		List<Integer> lottoNumbers = List.of(lotto.getNumber1(), lotto.getNumber2(), lotto.getNumber3(),
			lotto.getNumber4(), lotto.getNumber5(), lotto.getNumber6());
		return (int) lottoNumbers.stream()
			.filter(winningNumbers::contains)
			.count();
	}


}
