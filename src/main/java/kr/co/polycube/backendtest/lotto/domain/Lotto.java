package kr.co.polycube.backendtest.lotto.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Lotto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number_1")
	private Integer number1;

	@Column(name = "number_2")
	private Integer number2;

	@Column(name = "number_3")
	private Integer number3;

	@Column(name = "number_4")
	private Integer number4;

	@Column(name = "number_5")
	private Integer number5;

	@Column(name = "number_6")
	private Integer number6;
}
