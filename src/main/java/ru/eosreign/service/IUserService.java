package ru.eosreign.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.eosreign.entity.UserInfo;
import ru.eosreign.web.dto.PageableListUserResponse;
import ru.eosreign.web.dto.UserProfileRequest;
import ru.eosreign.web.dto.UserProfileResponse;

public interface IUserService {

    UserProfileResponse getUser(String jwtToken);

    UserInfo getByUsername(String username);

    UserDetailsService userDetailsService();

    UserInfo getCurrentUserByJwtToken(String jwtToken);

    void updateUser(String jwtToken, UserProfileRequest request);

    PageableListUserResponse getListUser(String jwtToken, int pageNum, int pageSize);

    void deleteUser(String jwtToken);
}
