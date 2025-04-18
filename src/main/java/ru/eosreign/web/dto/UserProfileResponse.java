package ru.eosreign.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.eosreign.web.dto.credential.UserCredentialResponse;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Тело ответа с данными пользователя")
public class UserProfileResponse {

    @JsonProperty("id")
    @Schema(description = "ID")
    private Long id;

    @JsonProperty("first_name")
    @Schema(description = "first_name")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "last_name")
    private String lastName;

    @JsonProperty("father_name")
    @Schema(description = "father_name")
    private String fatherName;

    @JsonProperty("avatar_url")
    @Schema(description = "avatar_url")
    private String avatarUrl;

    @JsonProperty("birth_date")
    @Schema(description = "birth_date")
    private LocalDate birthDate;

    @JsonProperty("credential")
    @Schema(description = "credential")
    private UserCredentialResponse credential;
}
