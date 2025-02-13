package kr.co.polycube.backendtest.users.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.polycube.backendtest.users.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
