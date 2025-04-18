package ru.eosreign.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eosreign.entity.UserCredential;

@Repository
public interface UserCredentialRepo extends JpaRepository<UserCredential, Long> {
}
