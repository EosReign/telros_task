package ru.eosreign.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с токеном доступа")
public class AccessTokenResponse {

    @JsonProperty("access_token")
    @Schema(
            description = "Токен доступа",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MR...")
    private String accessToken;
}
