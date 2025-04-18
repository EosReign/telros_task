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
public class UpdateCredentialEmailRequest {

    @JsonProperty("new_email")
    @Schema(description = "Новая почта", example = "test2@mail.ru")
    private String newEmail;
}
