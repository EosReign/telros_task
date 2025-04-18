package ru.eosreign.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.eosreign.service.auth.AuthService;
import ru.eosreign.web.dto.auth.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody RegisterRequest request) {
        service.register(request);
    }

    @PostMapping("/login/email")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse loginByEmail(@RequestBody LoginEmailRequest request) {
        return service.loginByEmail(request);
    }

    @PostMapping("/login/phone")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse loginByPhone(@RequestBody LoginPhoneRequest request) {
        return service.loginByPhone(request);
    }

    @PostMapping("/refresh/token")
    @ResponseStatus(HttpStatus.OK)
    public AccessTokenResponse refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        return service.refreshToken(request);
    }
}
