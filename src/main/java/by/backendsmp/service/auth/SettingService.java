package by.backendsmp.service.auth;

import by.backendsmp.config.jwt.JwtTokenProvider;
import by.backendsmp.entity.User;
import by.backendsmp.repository.UserRepository;
import by.backendsmp.web.dto.EditSettingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
}
