package kr.co.polycube.backendtest.lotto.dto.out;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddLottoResponseDto {

	private List<Integer> numbers;
}
