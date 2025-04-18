package ru.eosreign.web.dto.credential;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Тело запроса с новыми данными пользователя")
public class UpdateCredentialPasswordRequest {

    @JsonProperty("old_password")
    @Schema(description = "Старый пароль")
    private String oldPassword;

    @JsonProperty("new_password")
    @Schema(description = "Новый пароль")
    private String newPassword;

    @JsonProperty("repeat_new_password")
    @Schema(description = "Повторение нового пароля")
    private String repeatNewPassword;
}
