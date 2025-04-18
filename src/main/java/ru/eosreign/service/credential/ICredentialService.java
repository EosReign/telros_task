package ru.eosreign.service.credential;

import ru.eosreign.web.dto.credential.UpdateCredentialEmailRequest;
import ru.eosreign.web.dto.credential.UpdateCredentialPasswordRequest;
import ru.eosreign.web.dto.credential.UpdateCredentialPhoneRequest;

public interface ICredentialService {
    void updatePhone(String jwtToken, UpdateCredentialPhoneRequest dto);

    void updateEmail(String jwtToken, UpdateCredentialEmailRequest dto);

    void updatePassword(String jwtToken, UpdateCredentialPasswordRequest dto);
}
