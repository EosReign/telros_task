package ru.eosreign.service.credential;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.eosreign.entity.UserCredential;
import ru.eosreign.exception.InvalidArgumentException;
import ru.eosreign.repo.UserCredentialRepo;
import ru.eosreign.service.UserService;
import ru.eosreign.web.dto.credential.UpdateCredentialEmailRequest;
import ru.eosreign.web.dto.credential.UpdateCredentialPasswordRequest;
import ru.eosreign.web.dto.credential.UpdateCredentialPhoneRequest;

import static ru.eosreign.exception.FailedPreconditionException.INEQUALITY_PASSWORD_AND_REPEAT_PASSWORD;
import static ru.eosreign.exception.InvalidArgumentException.INVALID_OLD_PASSWORD;

@Service
@RequiredArgsConstructor
public class CredentialService implements ICredentialService {

    private final UserService userService;
    private final UserCredentialRepo repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updatePhone(String jwtToken, UpdateCredentialPhoneRequest dto) {
        UserCredential credential = userService.getCurrentUserByJwtToken(jwtToken)
                .getCredential();

        credential.setPhone(dto.getPhone());
        repo.save(credential);
    }

    @Override
    public void updateEmail(String jwtToken, UpdateCredentialEmailRequest dto) {
        UserCredential credential = userService.getCurrentUserByJwtToken(jwtToken)
                .getCredential();

        credential.setEmail(dto.getNewEmail());
        repo.save(credential);
    }

    @Override
    public void updatePassword(String jwtToken, UpdateCredentialPasswordRequest dto) {
        UserCredential credential = userService.getCurrentUserByJwtToken(jwtToken)
                .getCredential();

        validateEqualityOldPasswords(dto.getOldPassword(), credential.getPassword());
        validateEqualityNewPasswords(dto.getNewPassword(), dto.getRepeatNewPassword());

        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());

        credential.setPassword(encodedPassword);
        repo.save(credential);
    }

    private void validateEqualityNewPasswords(String newPassword, String repeatNewPassword) {
        if (!newPassword.equals(repeatNewPassword)) {
            throw new InvalidArgumentException(INEQUALITY_PASSWORD_AND_REPEAT_PASSWORD);
        }
    }

    private void validateEqualityOldPasswords(String oldDtoPassword, String oldEntityPassword) {
        if (!passwordEncoder.matches(oldDtoPassword, oldEntityPassword)) {
            throw new InvalidArgumentException(INVALID_OLD_PASSWORD);
        }
    }
}
