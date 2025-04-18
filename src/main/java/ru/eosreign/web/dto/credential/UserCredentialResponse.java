package ru.eosreign.web.dto.credential;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Тело ответа с данными безопасности")
public class UserCredentialResponse {

    @JsonProperty("id")
    @Schema(description = "ID", example = "1")
    private Long id;

    @JsonProperty("email")
    @Schema(description = "Почта", example = "test@mail.ru")
    private String email;

    @JsonProperty("phone")
    @Schema(description = "Номер телефона", example = "+71112223344")
    private String phone;
}
