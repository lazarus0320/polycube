package kr.co.polycube.backendtest.users.vo.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDetailRequestVo {
	private Long id;

	public static GetUserDetailRequestVo of(Long id) {
		return GetUserDetailRequestVo.builder()
				.id(id)
				.build();
	}
}
