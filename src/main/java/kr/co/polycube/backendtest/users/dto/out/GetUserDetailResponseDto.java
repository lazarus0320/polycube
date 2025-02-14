package kr.co.polycube.backendtest.users.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDetailResponseDto {
	private Long id;
	private String name;
}
