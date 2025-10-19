package by.backendsmp.service.auth;

import by.backendsmp.config.jwt.JwtTokenProvider;
import by.backendsmp.entity.User;
import by.backendsmp.repository.UserRepository;
import by.backendsmp.service.AvatarService;
import by.backendsmp.web.dto.EditSettingDTO;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AvatarService avatarService;

    public ResponseEntity<?> editProfile(String token, EditSettingDTO dto){
        String userName = jwtTokenProvider.refactorTokenToName(token);
        Optional<User> userData = userRepository.findUserByUserName(userName);
        if(userData.isPresent()){
            User user = userData.get();
            user.setDescription(dto.getDescription());
            user.setProfileAvatar(dto.getProfileAvatar());
            user.setUserAvatar(dto.getUserAvatar());
            user.setStreamKey(dto.getStreamKey());
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
        }
        return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.BAD_REQUEST);
    }

    public void saveAvatar(String token, MultipartFile file, String avatarProp) {
        String userName = jwtTokenProvider.refactorTokenToName(token);
        Optional<User> userData = userRepository.findUserByUserName(userName);
        if(userData.isPresent()){
            Long userId = userData.get().getId();
            if(avatarProp.equals("avatar")){
                String object = userId.toString() + "/" + "avatar";
                avatarService.uploadAvatarProfiles(object, file);
            } else if (avatarProp.equals("profile")) {
                String object = userId.toString() + "/" + "profile";
                avatarService.uploadAvatarProfiles(object, file);
            }
        }else{
            throw new RuntimeException("User not found");
        }
    }
}
