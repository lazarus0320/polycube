package kr.co.polycube.backendtest.users.application;

import org.springframework.stereotype.Service;

import kr.co.polycube.backendtest.common.error.BaseException;
import kr.co.polycube.backendtest.common.response.BaseResponseStatus;
import kr.co.polycube.backendtest.users.domain.Users;
import kr.co.polycube.backendtest.users.dto.AddUserRequestDto;
import kr.co.polycube.backendtest.users.dto.AddUserResponseDto;
import kr.co.polycube.backendtest.users.dto.GetUserDetailResponseDto;
import kr.co.polycube.backendtest.users.dto.in.GetUserDetailRequestDto;
import kr.co.polycube.backendtest.users.infrastructure.UsersRepository;
import kr.co.polycube.backendtest.users.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final UsersMapper usersMapper;

	@Override
	public AddUserResponseDto addUser(AddUserRequestDto requestDto) {

		Users newUser = usersMapper.addUsers(requestDto);
		usersRepository.save(newUser);

		return AddUserResponseDto.builder()
				.id(newUser.getId())
				.build();
	}

	@Override
	public GetUserDetailResponseDto getUserDetail(GetUserDetailRequestDto requestDto) {

		Users user = usersRepository.findById(requestDto.getId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_DATA));

		return usersMapper.toDto(user);
	}
}
