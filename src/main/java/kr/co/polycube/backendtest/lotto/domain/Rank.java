package kr.co.polycube.backendtest.lotto.domain;

import java.util.Arrays;
import java.util.Optional;

public enum Rank {

	FIRST(6),
	SECOND(5),
	THIRD(4),
	FOURTH(3),
	FIFTH(2),
	NONE(0);

	private final int matchCount;

	Rank(int matchCount) {
		this.matchCount = matchCount;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public static Optional<Rank> valueOfMatchCount(int matchCount) {
		return Arrays.stream(values())
			.filter(rank -> rank.matchCount == matchCount)
			.findFirst();
	}
}
