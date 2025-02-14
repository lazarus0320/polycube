package kr.co.polycube.backendtest.lotto.domain;

public enum Rank {

	FIRST(6),
	SECOND(5),
	THIRD(4),
	FOURTH(3),
	FIFTH(2);

	private final int matchCount;

	Rank(int matchCount) {
		this.matchCount = matchCount;
	}
}
