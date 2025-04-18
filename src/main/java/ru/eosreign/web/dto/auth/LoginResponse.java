package ru.eosreign.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c Jwt токенами: токен доступа, refresh токен")
public class LoginResponse {

    @JsonProperty("access_token")
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;

    @JsonProperty("refresh_token")
    @Schema(description = "Токен регенерации токена доступа", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String refreshToken;
}
