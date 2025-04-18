package ru.eosreign.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.eosreign.service.IUserService;
import ru.eosreign.service.avatar.UserAvatarService;
import ru.eosreign.web.dto.PageableListUserResponse;
import ru.eosreign.web.dto.UserProfileRequest;
import ru.eosreign.web.dto.UserProfileResponse;

import static ru.eosreign.web.WebHeader.AUTH_HEADER;

@RestController
@RequestMapping("/v1/user")
public class UserInfoController {

    @Qualifier("userService")
    private final IUserService service;

    private final UserAvatarService avatarService;

    public UserInfoController(IUserService service, UserAvatarService avatarService) {
        this.service = service;
        this.avatarService = avatarService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse getUser(HttpServletRequest request) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        return service.getUser(jwtToken);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(HttpServletRequest request, @RequestBody UserProfileRequest dto) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        service.updateUser(jwtToken, dto);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(HttpServletRequest request) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        service.deleteUser(jwtToken);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public PageableListUserResponse getListUser(HttpServletRequest request,
                                                @RequestParam(name = "pageNum") int pageNum,
                                                @RequestParam(name = "pageSize") int pageSize) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        return service.getListUser(jwtToken, pageNum, pageSize);
    }

    @PutMapping(value = "/avatar", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.OK)
    public void updateAvatar(HttpServletRequest request, @RequestParam("avatar") MultipartFile file) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        avatarService.setAvatar(jwtToken, file);
    }
}
