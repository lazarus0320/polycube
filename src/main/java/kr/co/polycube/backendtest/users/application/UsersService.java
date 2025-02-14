package kr.co.polycube.backendtest.users.application;

import kr.co.polycube.backendtest.users.dto.in.AddUserRequestDto;
import kr.co.polycube.backendtest.users.dto.out.AddUserResponseDto;
import kr.co.polycube.backendtest.users.dto.out.GetUserDetailResponseDto;
import kr.co.polycube.backendtest.users.dto.in.GetUserDetailRequestDto;
import kr.co.polycube.backendtest.users.dto.in.UpdateUserRequestDto;
import kr.co.polycube.backendtest.users.dto.out.UpdateUserResponseDto;

public interface UsersService {
	// 유저 등록
	AddUserResponseDto addUser(AddUserRequestDto requestDto);

	// 유저 상세 조회
	GetUserDetailResponseDto getUserDetail(GetUserDetailRequestDto requestDto);

	UpdateUserResponseDto updateUser(UpdateUserRequestDto dto);
}
