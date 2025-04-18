package ru.eosreign.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регенерацию AccessToken")
public class RefreshTokenRequest {

    @JsonProperty("refresh_token")
    @Schema(
            description = "Токен регенерации токена доступа",
            example = "eyJhbGciOiJIUzI1NiJ9...")
    @NotBlank
    private String refreshToken;
}
