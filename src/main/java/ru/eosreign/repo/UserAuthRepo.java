package ru.eosreign.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eosreign.entity.UserAuth;

@Repository
public interface UserAuthRepo extends JpaRepository<UserAuth, Long> {
}
