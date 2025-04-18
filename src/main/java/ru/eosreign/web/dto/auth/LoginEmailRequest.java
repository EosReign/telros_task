package ru.eosreign.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(requiredProperties = {"email", "password"},
        description = "LoginRequest by Email")
public class LoginEmailRequest {

    @JsonProperty("email")
    @Schema(description = "Почта пользователя", example = "test@gmail.com")
    @Size(min = 10, max = 64)
    @NotBlank
    private String email;

    @JsonProperty("password")
    @Schema(description = "Пароль пользователя", example = "8_DuR@le7KA")
    @Size(min = 8, max = 48)
    @NotBlank
    private String password;
}
