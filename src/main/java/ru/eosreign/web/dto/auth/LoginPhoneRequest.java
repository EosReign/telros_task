package ru.eosreign.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(requiredProperties = {"phone", "password"},
        description = "LoginRequest by Phone")
public class LoginPhoneRequest {

    @JsonProperty("phone")
    @Schema(description = "Номер телефона", example = "+71112223344")
    @Size(min = 12, max = 12)
    @NotBlank
    private String phone;

    @JsonProperty("password")
    @Schema(description = "Пароль пользователя", example = "8_DuR@le7KA")
    @Size(min = 8, max = 48)
    @NotBlank
    private String password;
}
