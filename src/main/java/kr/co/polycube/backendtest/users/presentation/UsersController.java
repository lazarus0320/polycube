package kr.co.polycube.backendtest.users.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.polycube.backendtest.common.response.BaseResponse;
import kr.co.polycube.backendtest.users.application.UsersService;
import kr.co.polycube.backendtest.users.dto.AddUserResponseDto;
import kr.co.polycube.backendtest.users.dto.GetUserDetailResponseDto;
import kr.co.polycube.backendtest.users.mapper.UsersMapper;
import kr.co.polycube.backendtest.users.vo.in.AddUserRequestVo;
import kr.co.polycube.backendtest.users.vo.in.GetUserDetailRequestVo;
import kr.co.polycube.backendtest.users.vo.out.GetUserDetailResponseVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 API", description = "유저 관련 API endpoints")
@RequestMapping("/users")
public class UsersController {
	// `/users` API를 호출하면, `{"id": ?}`을 응답한다.
	// `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.

	private final UsersService usersService;
	private final UsersMapper usersMapper;

	@Operation(summary = "유저 등록")
	@PostMapping
	public BaseResponse<AddUserResponseDto> getUser(AddUserRequestVo requestVo) {

		return new BaseResponse<>(
			usersService.addUser(usersMapper.toDto(requestVo))
		);
	}

	@Operation(summary = "유저 상세 조회", description = "유저 ID, Name 상세 조회")
	@GetMapping("/{id}")
	public BaseResponse<GetUserDetailResponseVo> getUserDetail(
		@PathVariable Long id
	) {

		GetUserDetailRequestVo requestVo = GetUserDetailRequestVo.of(id);
		GetUserDetailResponseDto responseDto = usersService.getUserDetail(usersMapper.toDto(requestVo));

		return new BaseResponse<>(
			usersMapper.toVo(responseDto)
		);
	}
}
