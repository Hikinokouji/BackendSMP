package by.backendsmp.controller.auth;

import by.backendsmp.config.jwt.JwtTokenProvider;
import by.backendsmp.service.AvatarService;
import by.backendsmp.service.auth.SettingService;
import by.backendsmp.service.user.UserService;
import by.backendsmp.web.dto.EditSettingDTO;
import by.backendsmp.web.dto.SettingUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sec")
@RequiredArgsConstructor
public class ProfilesController {
    private final UserService userService;
    private final SettingService settingService;
    private final JwtTokenProvider jwtTokenProvider;

    private final String avatarAvatar  = "avatar";
    private final String profileAvatar = "profile";


    @GetMapping("/data")
    public ResponseEntity<?> getDateUserForSetting(@RequestHeader("Authorization") String token) {
        String userName = jwtTokenProvider.refactorTokenToName(token);
        SettingUserDTO userDTO = userService.findUserByUserNameForSetting(userName);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/data")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token,
                                                @RequestBody EditSettingDTO dto) {
        return settingService.editProfile(token, dto);
    }

    @PostMapping("/us")
    public ResponseEntity<?> saveUserAvatar(@RequestHeader("Authorization") String token,
                                               @RequestParam("file") MultipartFile file) {
        settingService.saveAvatar(token, file, avatarAvatar);
        return ResponseEntity.ok("Avatar user uploaded successfully");
    }

    @PostMapping("/pr")
    public ResponseEntity<?> saveProfileAvatar(@RequestHeader("Authorization") String token,
                                            @RequestParam("file") MultipartFile file) {
        settingService.saveAvatar(token, file, profileAvatar);
        return ResponseEntity.ok("Avatar profile uploaded successfully");
    }
}
