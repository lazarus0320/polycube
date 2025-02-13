package kr.co.polycube.backendtest.users.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDetailResponseVo {
	private Long id;
	private String name;
}
