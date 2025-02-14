package kr.co.polycube.backendtest.users.mapper;

import org.springframework.stereotype.Component;

import kr.co.polycube.backendtest.users.domain.Users;
import kr.co.polycube.backendtest.users.dto.AddUserRequestDto;
import kr.co.polycube.backendtest.users.dto.GetUserDetailResponseDto;
import kr.co.polycube.backendtest.users.dto.in.GetUserDetailRequestDto;
import kr.co.polycube.backendtest.users.dto.in.UpdateUserRequestDto;
import kr.co.polycube.backendtest.users.dto.out.UpdateUserResponseDto;
import kr.co.polycube.backendtest.users.vo.in.AddUserRequestVo;
import kr.co.polycube.backendtest.users.vo.in.GetUserDetailRequestVo;
import kr.co.polycube.backendtest.users.vo.in.UpdateUserRequestVo;
import kr.co.polycube.backendtest.users.vo.out.GetUserDetailResponseVo;
import kr.co.polycube.backendtest.users.vo.out.UpdateUserResponseVo;

@Component
public class UsersMapper {

	public Users toEntity(AddUserRequestDto requestDto) {

		return Users.builder()
			.name(requestDto.getName())
			.build();
	}

	public AddUserRequestDto toDto(AddUserRequestVo requestVo) {

		return AddUserRequestDto.builder()
			.name(requestVo.getName())
			.build();
	}

	public GetUserDetailResponseDto toGetUserDetailDto(Users user) {

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

	public UpdateUserRequestDto toDto(Long id, UpdateUserRequestVo requestVo) {

		return UpdateUserRequestDto.builder()
			.id(id)
			.name(requestVo.getName())
			.build();
	}

	public UpdateUserResponseVo toVo(UpdateUserResponseDto responseDto) {

		return UpdateUserResponseVo.builder()
			.id(responseDto.getId())
			.name(responseDto.getName())
			.build();
	}

	public UpdateUserResponseDto toUpdateUserDto(Users user) {

		return UpdateUserResponseDto.builder()
			.id(user.getId())
			.name(user.getName())
			.build();
	}
}