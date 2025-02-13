package kr.co.polycube.backendtest.users.mapper;

import org.springframework.stereotype.Component;

import kr.co.polycube.backendtest.users.domain.Users;
import kr.co.polycube.backendtest.users.dto.AddUserRequestDto;
import kr.co.polycube.backendtest.users.dto.GetUserDetailResponseDto;
import kr.co.polycube.backendtest.users.dto.in.GetUserDetailRequestDto;
import kr.co.polycube.backendtest.users.vo.in.AddUserRequestVo;
import kr.co.polycube.backendtest.users.vo.in.GetUserDetailRequestVo;
import kr.co.polycube.backendtest.users.vo.out.GetUserDetailResponseVo;

@Component
public class UsersMapper {

	public Users addUsers(AddUserRequestDto requestDto) {
		return Users.builder()
				.name(requestDto.getName())
				.build();
	}

	public AddUserRequestDto toDto(AddUserRequestVo requestVo) {
		return AddUserRequestDto.builder()
				.name(requestVo.getName())
				.build();
	}

	public GetUserDetailResponseDto toDto(Users user) {
		return GetUserDetailResponseDto.builder()
				.id(user.getId())
				.name(user.getName())
				.build();
	}

	public GetUserDetailRequestDto toDto(GetUserDetailRequestVo requestVo) {
		return GetUserDetailRequestDto.builder()
				.id(requestVo.getId())
				.build();
	}

	public GetUserDetailResponseVo toVo(GetUserDetailResponseDto responseDto) {
		return GetUserDetailResponseVo.builder()
				.id(responseDto.getId())
				.name(responseDto.getName())
				.build();
	}
}
