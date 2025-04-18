package ru.eosreign.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(requiredProperties = {
                "firstName",
                "lastName",
                "phoneNumber",
                "email",
                "password",
                "repeatPassword"})
public class RegisterRequest {

    @JsonProperty("first_name")
    @Schema(description = "Имя пользователя", example = "Иван")
    @Size(max = 30, message = "Имя может содержать максимум 30 символов")
    @NotBlank
    @Pattern(regexp = "^(?:[А-Яа-я-']+|[a-zA-Z-']+)$",
            message = "Для заполнения имени разрешены символы кириллицы или латиницы, дефис и апостроф")
    private String firstName;

    @JsonProperty("last_name")
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @Size(max = 30, message = "Фамилия может содержать максимум 30 символов")
    @NotBlank
    @Pattern(regexp = "^(?:[А-Яа-я-']+|[a-zA-Z-']+)$",
            message = "Для заполнения фамилии разрешены символы кириллицы или латиницы, дефис и апостроф")
    private String lastName;

    @JsonProperty("phone_number")
    @Schema(description = "Телефон пользователя", example = "+75678903232")
    @Size(min = 12, max = 12, message = "Номер телефона должен содержать + и далее 10 цифр")
    @NotBlank
    @Pattern(regexp = "^\\+\\d+$", message = "Введите номер телефона в формате +7")
    private String phoneNumber;

    @JsonProperty("email")
    @Schema(description = "Почта пользователя", example = "test@gmail.com")
    @Size(min = 10, max = 64, message = "Адрес почты должен содержать от 10 до 64 символов")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}.\\-~]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Невалидный адрес электронной почты")
    private String email;

    @JsonProperty("password")
    @Schema(description = "Пароль пользователя", example = "8_DuR@le7KA")
    @Size(min = 8, max = 48, message = "Пароль должен содержать от 8 до 48 символов")
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?!.*\\s)(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,48}$",
            message = "Пароль обязательно должен содержать буквы (латиница), одну цифру и один спец символ, без пробелов")
    private String password;

    @JsonProperty("repeat_password")
    @Schema(description = "Повтор пароля пользователя", example = "8_DuR@le7KA")
    @Size(min = 8, max = 48, message = "Пароль должен содержать от 8 до 48 символов")
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?!.*\\s)(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,48}$",
            message = "Пароль обязательно должен содержать буквы (латиница), одну цифру и один спец символ, без пробелов")
    private String repeatPassword;
}
