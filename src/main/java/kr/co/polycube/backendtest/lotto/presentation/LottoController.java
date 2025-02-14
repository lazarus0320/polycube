package kr.co.polycube.backendtest.lotto.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.polycube.backendtest.common.response.BaseResponse;
import kr.co.polycube.backendtest.lotto.application.LottoService;
import kr.co.polycube.backendtest.lotto.mapper.LottoMapper;
import kr.co.polycube.backendtest.lotto.vo.out.AddLottoResponseVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "로또 API", description = "로또 관련 API endpoints")
@RequestMapping("/lottos")
public class LottoController {

	private final LottoMapper lottoMapper;
	private final LottoService lottoService;

	@Operation(summary = "로또 번호 발급")
	@PostMapping
	public BaseResponse<AddLottoResponseVo> addLotto() {

		return new BaseResponse<>(
			lottoMapper.toVo(lottoService.addLotto())
		);
	}
}
