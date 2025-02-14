package kr.co.polycube.backendtest.lotto.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.polycube.backendtest.lotto.domain.Winner;

@Repository
public interface WinnerRepository extends JpaRepository<Winner, Long> {

}
