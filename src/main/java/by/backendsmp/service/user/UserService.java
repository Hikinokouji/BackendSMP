package by.backendsmp.service.user;

import by.backendsmp.config.jwt.JwtTokenProvider;
import by.backendsmp.entity.User;
import by.backendsmp.entity.users.UserMainPageResponse;
import by.backendsmp.repository.UserRepository;
import by.backendsmp.web.dto.SettingUserDTO;
import by.backendsmp.web.dto.jwt.JwtRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getById(Long userId) {
        return userRepository.getById(userId);
    }

    public User findUserByUserName(String userName) {
        Optional<User> user = userRepository.findUserByUserName(userName);
        if(user.isPresent()){
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    public SettingUserDTO findUserByUserNameForSetting(String userName) {
        Optional<User> user = userRepository.findUserByUserName(userName);
        if(user.isPresent()){
            User userDto = user.get();
            SettingUserDTO settingUserDTO = new SettingUserDTO();
            settingUserDTO.setUserName(userDto.getUserName());
            settingUserDTO.setEmail(userDto.getEmail());
            settingUserDTO.setBirthDate(userDto.getBirthDate());
            settingUserDTO.setStreamKey(userDto.getStreamKey());
            settingUserDTO.setDescription(userDto.getDescription());
            return settingUserDTO;
        }
        throw new UsernameNotFoundException("User not found");
    }

    public Optional<UserMainPageResponse> searchUserPage(String userName) {
        return userRepository.customFindUserWithRelations(userName)
                .map(user -> {
                    UserMainPageResponse dto = new UserMainPageResponse();
                    dto.setUserName(user.getUserName());
                    dto.setDescription(user.getDescription());
                    dto.setCreatedDate(user.getCreatedAt());

                    dto.setUserAvatar(user.getUserAvatar());
                    dto.setProfileAvatar(user.getProfileAvatar());

                    dto.setFollowersCount(user.getFollowers().size());
                    dto.setStreamersCount(user.getFollowing().size());

                    dto.setFollowers(
                            user.getFollowers().stream()
                                    .map(f -> f.getFollower().getUserName())
                                    .toList()
                    );

                    dto.setStreamers(
                            user.getFollowing().stream()
                                    .map(f -> f.getStreamer().getUserName())
                                    .toList()
                    );

                    return dto;
                });
    }

    public Long getIdByUsername(String userName){
        return userRepository.customFindUserIdByUserName(userName);
    }
}
