package ru.eosreign.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.eosreign.entity.UserInfo;
import ru.eosreign.exception.NotFoundException;
import ru.eosreign.mapper.UserMapper;
import ru.eosreign.repo.UserInfoRepo;
import ru.eosreign.util.JwtService;
import ru.eosreign.web.dto.PageableListUserResponse;
import ru.eosreign.web.dto.UserProfileRequest;
import ru.eosreign.web.dto.UserProfileResponse;

import java.sql.Timestamp;

import static ru.eosreign.exception.NotFoundException.F_USER_EMAIL_MESSAGE;

@Service
public class UserService implements IUserService {

    private final UserInfoRepo repo;
    private final JwtService jwtService;
    private final UserMapper mapper;

    public UserService(UserInfoRepo repo, JwtService jwtService, UserMapper mapper) {
        this.repo = repo;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    @Override
    public UserProfileResponse getUser(String jwtToken) {
        UserInfo user = getCurrentUserByJwtToken(jwtToken);

        return mapper.entityToDto(user);
    }

    @Override
    public UserInfo getByUsername(String username) {
        return repo.findByCredential_Email(username)
                .orElseThrow(() -> new NotFoundException(
                            String.format(F_USER_EMAIL_MESSAGE, username)));
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public UserInfo getCurrentUserByJwtToken(String jwtToken) {
        String email = jwtService.extractUsernameFromAuthHeader(jwtToken);
        return getByUsername(email);
    }

    @Override
    public void updateUser(String jwtToken, UserProfileRequest request) {
        UserInfo user = getCurrentUserByJwtToken(jwtToken);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFatherName(request.getFatherName());

        if (request.getBirthDate() != null) {
            user.setBirthDate(Timestamp.valueOf(request.getBirthDate().atStartOfDay()));
        } else {
            user.setBirthDate(null);
        }
        repo.save(user);
    }

    @Override
    public PageableListUserResponse getListUser(String jwtToken, int pageNum, int pageSize) {
        Page<UserInfo> pageEntity = repo.findAll(
                PageRequest.of(pageNum - 1, pageSize));
        return mapper.entityToDto(pageEntity);

    }

    @Override
    public void deleteUser(String jwtToken) {
        UserInfo currentUser = getCurrentUserByJwtToken(jwtToken);

        repo.delete(currentUser);
    }
}
