package ru.eosreign.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Тело листа пользователя с пагинацией")
public class PageableListUserResponse {

    @Schema(description = "Количество записей на страницу", example = "3")
    @JsonProperty("count")
    private int count;

    @Schema(description = "Номер текущей страницы", example = "1")
    @JsonProperty("current_page")
    private int currentPage;

    @Schema(description = "Общее количество страниц", example = "20")
    @JsonProperty("total_pages")
    private int totalPages;

    @Schema(description = "Список выдачи")
    @JsonProperty("user_list")
    private List<UserProfileResponse> userList;
}
