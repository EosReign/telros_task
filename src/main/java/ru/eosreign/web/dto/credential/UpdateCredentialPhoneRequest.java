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
public class UpdateCredentialPhoneRequest {

    @JsonProperty("phone")
    @Schema(description = "Номер телефона", example = "+71112223344")
    private String phone;
}
