package kr.co.polycube.backendtest.users.application;

import kr.co.polycube.backendtest.users.dto.AddUserRequestDto;
import kr.co.polycube.backendtest.users.dto.AddUserResponseDto;
import kr.co.polycube.backendtest.users.dto.GetUserDetailResponseDto;
import kr.co.polycube.backendtest.users.dto.in.GetUserDetailRequestDto;

public interface UsersService {
	// 유저 등록
	AddUserResponseDto addUser(AddUserRequestDto requestDto);

	// 유저 상세 조회
	GetUserDetailResponseDto getUserDetail(GetUserDetailRequestDto requestDto);
}
