package kr.co.polycube.backendtest.lotto.application;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.polycube.backendtest.lotto.domain.Lotto;
import kr.co.polycube.backendtest.lotto.dto.out.AddLottoResponseDto;
import kr.co.polycube.backendtest.lotto.infrastructure.LottoRepository;
import kr.co.polycube.backendtest.lotto.mapper.LottoMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LottoServiceImpl implements LottoService {

	private final LottoRepository lottoRepository;
	private final LottoMapper lottoMapper;

	@Override
	public AddLottoResponseDto addLotto() {

		SecureRandom secureRandom = new SecureRandom();

		List<Integer> numbers = secureRandom.ints(1, 46)
			.distinct()
			.limit(6)
			.sorted()
			.boxed()
			.toList();

		Lotto lotto = Lotto.builder()
			.number1(numbers.get(0))
			.number2(numbers.get(1))
			.number3(numbers.get(2))
			.number4(numbers.get(3))
			.number5(numbers.get(4))
			.number6(numbers.get(5))
			.build();

		lottoRepository.save(lotto);

		return lottoMapper.toDto(lotto);
	}
}
