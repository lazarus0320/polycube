package kr.co.polycube.backendtest.users.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.polycube.backendtest.common.error.BaseException;
import kr.co.polycube.backendtest.common.response.BaseResponseStatus;
import kr.co.polycube.backendtest.users.domain.Users;
import kr.co.polycube.backendtest.users.dto.in.AddUserRequestDto;
import kr.co.polycube.backendtest.users.dto.out.AddUserResponseDto;
import kr.co.polycube.backendtest.users.dto.out.GetUserDetailResponseDto;
import kr.co.polycube.backendtest.users.dto.in.GetUserDetailRequestDto;
import kr.co.polycube.backendtest.users.dto.in.UpdateUserRequestDto;
import kr.co.polycube.backendtest.users.dto.out.UpdateUserResponseDto;
import kr.co.polycube.backendtest.users.infrastructure.UsersRepository;
import kr.co.polycube.backendtest.users.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final UsersMapper usersMapper;

	@Override
	public AddUserResponseDto addUser(AddUserRequestDto requestDto) {

		Users newUser = usersMapper.toEntity(requestDto);
		usersRepository.save(newUser);

		return AddUserResponseDto.builder()
				.id(newUser.getId())
				.build();
	}

	@Override
	@Transactional(readOnly = true)
	public GetUserDetailResponseDto getUserDetail(GetUserDetailRequestDto requestDto) {

		Users user = usersRepository.findById(requestDto.getId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_DATA));

		return usersMapper.toGetUserDetailDto(user);
	}

	@Override
	public UpdateUserResponseDto updateUser(UpdateUserRequestDto requestDto) {

		Users user = usersRepository.findById(requestDto.getId())
			.orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FOUND_DATA));

		user.update(requestDto);

		return usersMapper.toUpdateUserDto(user);
	}
}
