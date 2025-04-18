package ru.eosreign.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Тело запроса с новыми данными пользователя")
public class UserProfileRequest {

    @JsonProperty("first_name")
    @Schema(description = "Имя", example = "Иван")
    @NotNull
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "Фамилия", example = "Иванов")
    @NotNull
    private String lastName;

    @JsonProperty("father_name")
    @Schema(description = "Отчество", example = "Иванович")
    private String fatherName;

    @JsonProperty("birth_date")
    @Schema(description = "Дата рождения")
    private LocalDate birthDate;
}
