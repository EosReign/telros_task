package ru.eosreign.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.eosreign.entity.UserAuth;
import ru.eosreign.entity.UserCredential;
import ru.eosreign.entity.UserInfo;
import ru.eosreign.exception.AlreadyExistsException;
import ru.eosreign.exception.FailedPreconditionException;
import ru.eosreign.exception.NotFoundException;
import ru.eosreign.exception.PermissionDeniedException;
import ru.eosreign.repo.UserAuthRepo;
import ru.eosreign.repo.UserInfoRepo;
import ru.eosreign.util.JwtService;
import ru.eosreign.web.dto.auth.*;

import java.sql.Timestamp;

import static ru.eosreign.exception.AlreadyExistsException.F_USER_EMAIL_ALREADY_EXIST;
import static ru.eosreign.exception.AlreadyExistsException.F_USER_PHONE_ALREADY_EXIST;
import static ru.eosreign.exception.FailedPreconditionException.INEQUALITY_PASSWORD_AND_REPEAT_PASSWORD;
import static ru.eosreign.exception.NotFoundException.F_USER_EMAIL_MESSAGE;
import static ru.eosreign.exception.NotFoundException.F_USER_PHONE_MESSAGE;
import static ru.eosreign.exception.PermissionDeniedException.TOKEN_EXPIRED_REASON;

@Service
public class AuthService {

    @Value("${token.access.expiration}")
    private String jwtAccessExpiration;

    @Value("${token.refresh.expiration}")
    private String jwtRefreshExpiration;

    @Value("${minio.default.url.avatar}")
    private String defaultAvatarUrl;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserInfoRepo userRepo;
    private final UserAuthRepo repo;

    public AuthService(PasswordEncoder passwordEncoder, JwtService jwtService, UserInfoRepo userRepo, UserAuthRepo repo) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.repo = repo;
    }

    public void register(RegisterRequest request) {
        String email = request.getEmail();

        if (userRepo.existsByCredential_Email(email)) {
            throw new AlreadyExistsException(
                    String.format(F_USER_EMAIL_ALREADY_EXIST, email));
        }

        if (userRepo.existsByCredential_Phone(request.getPhoneNumber())) {
            throw new AlreadyExistsException(
                    String.format(F_USER_PHONE_ALREADY_EXIST, email));
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new FailedPreconditionException(INEQUALITY_PASSWORD_AND_REPEAT_PASSWORD);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserInfo user = UserInfo.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .avatarUrl(defaultAvatarUrl)
                .credential(
                        UserCredential.builder()
                                .email(request.getEmail())
                                .phone(request.getPhoneNumber())
                                .password(encodedPassword)
                                .role("ROLE_USER")
                                .auth(new UserAuth())
                                .build())
                .build();
        userRepo.save(user);
    }

    public LoginResponse loginByEmail(LoginEmailRequest request) {
        String email = request.getEmail();
        UserInfo userInfo = userRepo.findByCredential_Email(email)
                .orElseThrow(() -> new NotFoundException(
                            String.format(F_USER_EMAIL_MESSAGE, email)));

        String accessToken = jwtService.generateAccessToken(userInfo);
        String refreshToken = jwtService.generateRefreshToken(userInfo);
        Timestamp refreshExpiration = createExpiration(jwtRefreshExpiration);

        UserAuth userAuth = UserAuth.builder()
                .id(userInfo
                        .getCredential()
                        .getAuth()
                        .getId())
                .refreshToken(refreshToken)
                .expirationTime(refreshExpiration)
                .build();

        repo.save(userAuth);

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse loginByPhone(LoginPhoneRequest request) {
        String phone = request.getPhone();
        UserInfo userInfo = userRepo.findByCredential_Phone(phone)
                .orElseThrow(() -> new NotFoundException(
                        String.format(F_USER_PHONE_MESSAGE, phone)));

        String accessToken = jwtService.generateAccessToken(userInfo);
        String refreshToken = jwtService.generateRefreshToken(userInfo);
        Timestamp refreshExpiration = createExpiration(jwtRefreshExpiration);

        UserAuth userAuth = UserAuth.builder()
                .id(userInfo
                        .getCredential()
                        .getAuth()
                        .getId())
                .refreshToken(refreshToken)
                .expirationTime(refreshExpiration)
                .build();

        repo.save(userAuth);

        return new LoginResponse(accessToken, refreshToken);
    }

    public AccessTokenResponse refreshToken(RefreshTokenRequest request) {
        if (jwtService.isTokenExpired(request.getRefreshToken())) {
            throw new PermissionDeniedException(TOKEN_EXPIRED_REASON);
        }
        String email = jwtService.extractUsername(request.getRefreshToken());

        UserInfo userInfo = userRepo.findByCredential_Email(email)
                .orElseThrow(() -> new NotFoundException(
                        String.format(F_USER_EMAIL_MESSAGE, email)));

        String accessToken = jwtService.generateAccessToken(userInfo);

        return new AccessTokenResponse(accessToken);
    }

    private Timestamp createExpiration(String liveTime) {
        return new Timestamp(
                System.currentTimeMillis() + Long.parseLong(liveTime)
        );
    }
}
