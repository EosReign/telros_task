package ru.eosreign.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.eosreign.entity.UserInfo;
import ru.eosreign.web.dto.PageableListUserResponse;
import ru.eosreign.web.dto.UserProfileResponse;
import ru.eosreign.web.dto.credential.UserCredentialResponse;

import java.util.List;

@Component
public class UserMapper {

    public PageableListUserResponse entityToDto(Page<UserInfo> entity) {
        List<UserProfileResponse> userList = entity.get()
                .map(this::entityToDto)
                .toList();

        return PageableListUserResponse.builder()
                .count(entity.getSize())
                .currentPage(entity.getNumber())
                .totalPages(entity.getTotalPages())
                .userList(userList)
                .build();
    }

    public UserProfileResponse entityToDto(UserInfo entity) {
        var credential = UserCredentialResponse.builder()
                .id(entity.getCredential().getId())
                .email(entity.getCredential().getEmail())
                .phone(entity.getCredential().getPhone())
                .build();
        return UserProfileResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .fatherName(entity.getFatherName())
                .avatarUrl(entity.getAvatarUrl())
                .birthDate(
                        entity.getBirthDate() != null ?
                                entity.getBirthDate().toLocalDateTime().toLocalDate() :
                                null)
                .credential(credential)
                .build();
    }
}
