package ru.eosreign.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.eosreign.service.credential.ICredentialService;
import ru.eosreign.web.dto.credential.UpdateCredentialEmailRequest;
import ru.eosreign.web.dto.credential.UpdateCredentialPasswordRequest;
import ru.eosreign.web.dto.credential.UpdateCredentialPhoneRequest;

import static ru.eosreign.web.WebHeader.AUTH_HEADER;

@RestController
@RequestMapping("/v1/user/credential")
@RequiredArgsConstructor
public class UserCredentialController {

    private final ICredentialService service;

    @PostMapping("/phone")
    @ResponseStatus(HttpStatus.OK)
    public void updatePhone(HttpServletRequest request, @RequestBody UpdateCredentialPhoneRequest dto) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        service.updatePhone(jwtToken, dto);
    }

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmail(HttpServletRequest request, @RequestBody UpdateCredentialEmailRequest dto) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        service.updateEmail(jwtToken, dto);
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(HttpServletRequest request, @RequestBody UpdateCredentialPasswordRequest dto) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        service.updatePassword(jwtToken, dto);
    }

}
