package by.backendsmp.controller.unauth;

import by.backendsmp.entity.users.UserMainPageResponse;
import by.backendsmp.service.AvatarService;
import by.backendsmp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AvatarService avatarService;

    private final String avatar = "avatar";
    private final String profile = "profile";
    private final String fullAvatar = "full_avatar";
    private final String testIpAddress = "10.182.81.177";

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam("user") String userName){
        Optional<UserMainPageResponse> userMainPageResponse = userService.searchUserPage(userName);
        if(!userMainPageResponse.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
        return ResponseEntity.ok(userMainPageResponse);
    }

    @GetMapping("/getAvatarUser")
    public ResponseEntity<?> getAvatarUser(@RequestParam("user") String userName){
        Long userId = userService.getIdByUsername(userName);
        Map<String, String> presignedUrl = avatarService.getAvatar(userId, avatar);
        return ResponseEntity.ok(presignedUrl);
    }

    @GetMapping("/getAvatarUserProfile")
    public ResponseEntity<?> getAvatarProfile(@RequestParam("user") String userName){
        Long userId = userService.getIdByUsername(userName);
        Map<String, String> presignedUrl = avatarService.getAvatar(userId, profile);
        return ResponseEntity.ok(presignedUrl);
    }

    @GetMapping("/getAvatars")
    public ResponseEntity<?> getAvatars(@RequestParam("user") String userName){
        Long userId = userService.getIdByUsername(userName);
        Map<String, String> presignedUrl = avatarService.getAvatar(userId, fullAvatar);
        log.info("Return Get Avatars: ", presignedUrl);
        return ResponseEntity.ok(presignedUrl);
    }
}
