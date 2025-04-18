package ru.eosreign.service.avatar;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.eosreign.entity.UserInfo;
import ru.eosreign.repo.UserInfoRepo;
import ru.eosreign.service.IUserService;
import ru.eosreign.util.file.IFileService;

@Service
public class UserAvatarService {

    @Qualifier("imageService")
    private final IFileService imageService;
    @Qualifier("userService")
    private final IUserService userService;
    private final UserInfoRepo repo;

    @Value("${minio.default.url.avatar}")
    private String defaultAvatarUrl;
    private static final String AVATAR_BUCKET_NAME = "avatar";
    private static final String FILE_PATH = "/";

    public UserAvatarService(IFileService imageService, IUserService userService, UserInfoRepo repo) {
        this.imageService = imageService;
        this.userService = userService;
        this.repo = repo;
    }

    public void setAvatar(String jwtToken, MultipartFile avatar) {
        UserInfo user = userService.getCurrentUserByJwtToken(jwtToken);

        String oldAvatarUrl = user.getAvatarUrl();
        String newAvatarUrl = imageService.setFile(
                String.valueOf(user.getId()),
                avatar,
                AVATAR_BUCKET_NAME,
                FILE_PATH);

        if (!oldAvatarUrl.equals(newAvatarUrl)) {
            user.setAvatarUrl(newAvatarUrl);
            if (!oldAvatarUrl.equals(defaultAvatarUrl)) {
                imageService.deleteFile(oldAvatarUrl, AVATAR_BUCKET_NAME);
            }
        }
        user.setAvatarUrl(newAvatarUrl);
        repo.save(user);
    }
}
