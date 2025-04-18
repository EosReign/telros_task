package ru.eosreign.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.eosreign.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

    boolean existsByCredential_Email(String email);

    Optional<UserInfo> findByCredential_Email(String email);

    Optional<UserInfo> findByCredential_Phone(String phone);

    boolean existsByCredential_Phone(String phoneNumber);
}
