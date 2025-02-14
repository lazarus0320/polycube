package kr.co.polycube.backendtest.lotto.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.polycube.backendtest.lotto.domain.Lotto;

@Repository
public interface LottoRepository extends JpaRepository<Lotto, Long> {
}
