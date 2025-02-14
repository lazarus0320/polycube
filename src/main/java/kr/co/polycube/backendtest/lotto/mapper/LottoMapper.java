package kr.co.polycube.backendtest.lotto.mapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import kr.co.polycube.backendtest.lotto.domain.Lotto;
import kr.co.polycube.backendtest.lotto.dto.out.AddLottoResponseDto;
import kr.co.polycube.backendtest.lotto.vo.out.AddLottoResponseVo;

@Component
public class LottoMapper {

	public AddLottoResponseDto toDto(Lotto lotto) {

		return AddLottoResponseDto.builder()
			.numbers(Arrays.asList(
				lotto.getNumber1(),
				lotto.getNumber2(),
				lotto.getNumber3(),
				lotto.getNumber4(),
				lotto.getNumber5(),
				lotto.getNumber6()
			))
			.build();
	}

	public AddLottoResponseVo toVo(AddLottoResponseDto addLottoResponseDto) {

		return AddLottoResponseVo.builder()
			.numbers(addLottoResponseDto.getNumbers())
			.build();
	}
}
